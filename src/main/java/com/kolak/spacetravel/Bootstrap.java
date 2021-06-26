package com.kolak.spacetravel;

import com.kolak.spacetravel.model.User;
import com.kolak.spacetravel.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Bootstrap {

    private final UserRepo userRepo;

    @Autowired
    public Bootstrap(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void bootstrap() {
        Optional<User> administrator = userRepo.findByUsername("Administrator");
        if (!administrator.isPresent()) {
            userRepo.save(new User("ROLE_ADMIN",
                    "Administrator",
                    new BCryptPasswordEncoder().encode("Abc12345"),
                    ""));
        }
    }

}
