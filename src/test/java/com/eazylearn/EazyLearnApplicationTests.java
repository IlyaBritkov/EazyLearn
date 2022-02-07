package com.eazylearn;

import com.eazylearn.entity.Role;
import com.eazylearn.entity.User;
import com.eazylearn.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.eazylearn.enums.UserRole.ADMIN;
import static com.eazylearn.enums.UserStatus.ACTIVE;

@SpringBootTest
@ActiveProfiles("dev")
//@RunWith(SpringRunner.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = EazyLearnApplication.class)
//@ContextConfiguration(classes = {BeansConfig.class, SecurityConfig.class, Swagger2Config.class})
//@ExtendWith(MockitoExtension.class)
//@Disabled // todo: active it
public class EazyLearnApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    public void contextLoads() {
    }

    @Test
    @Disabled
    public void createAdminUser() {
        User admin = User.builder()
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin"))
                .status(ACTIVE)
                .roles(List.of(new Role(ADMIN)))
                .build();

        System.out.println(userRepository);
//        userRepository.save(admin);
    }

}
