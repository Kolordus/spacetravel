package com.kolak.spacetravel.contoller;

import com.kolak.spacetravel.model.User;
import com.kolak.spacetravel.security.util.JwtResponse;
import com.kolak.spacetravel.security.util.JwtUtil;
import com.kolak.spacetravel.security.util.LoginCredentials;
import com.kolak.spacetravel.service.UserService;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")

public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    @PermitAll()
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/is-user-taken/{username}")
    public boolean isUsernameTaken(@PathVariable String username) {
        return this.userService.isUserTaken(username);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<String>> getUsersInfo(@PathVariable String username) {
        List<String> userInfo = new ArrayList<>();
//        userInfo.add(userService.getUsersInfo(username).getRole());
//        userInfo.add(userService.getUsersInfo(username).getId().toString());

        // todo do mapy z tym
        return ResponseEntity.ok(userInfo);
    }

    @PermitAll()
    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginCredentials loginCredentials) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginCredentials.getUsername(), loginCredentials.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        logger.info("JWT given");
        return new ResponseEntity<>(jwtUtil.generateToken(loginCredentials.getUsername()), HttpStatus.OK);
    }

    @PermitAll()
    @GetMapping("/refreshtoken")
    public ResponseEntity<JwtResponse> refreshtoken(HttpServletRequest request) {
        logger.info("refresh token invoked!");
        // From the HttpRequest get the claims
        Optional<DefaultClaims> claims = Optional.ofNullable((DefaultClaims) request.getAttribute("claims"));
        if (claims.isPresent()) {
            Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims.get());
            return ResponseEntity.ok(jwtUtil.generateRefreshToken(expectedMap, expectedMap.get("sub").toString()));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }
}
