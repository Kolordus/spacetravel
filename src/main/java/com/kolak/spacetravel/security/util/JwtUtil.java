package com.kolak.spacetravel.security.util;

import com.kolak.spacetravel.model.User;
import com.kolak.spacetravel.security.UserDetailedServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final UserDetailedServiceImpl userDetailedService;
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${jwt.expirationTime}")
    private Long EXPIRATION_TIME;

    @Autowired
    public JwtUtil(UserDetailedServiceImpl userDetailedService) {
        this.userDetailedService = userDetailedService;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public JwtResponse generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetailedService.loadUserByUsername(username));
    }

    private JwtResponse createToken(Map<String, Object> claims, User userDetails) {
        claims.put("role", userDetails.getAuthorities());
        claims.put("userId", userDetails.getId());
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
        return new JwtResponse(jwt);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public JwtResponse generateRefreshToken(Map<String, Object> claims, String subject) {
        String jwt = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
        return new JwtResponse(jwt);
    }

}
