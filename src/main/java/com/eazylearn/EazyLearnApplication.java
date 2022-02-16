package com.eazylearn;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class EazyLearnApplication { // todo: add indexes to the database

    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(EazyLearnApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(UserRepository userRepository) {
//        return args -> {
//            com.eazylearn.entity.User newUser = User.builder()
//                    .username("admin")
//                    .email("admin@gmail.com")
//                    .password(passwordEncoder.encode("admin"))
//                    .status(UserStatus.ACTIVE)
//                    .roles(java.util.Collections.singletonList(new com.eazylearn.entity.Role(com.eazylearn.enums.UserRole.ROLE_ADMIN)))
//                    .build();
//            userRepository.save(newUser);
//        };
//    }

}
