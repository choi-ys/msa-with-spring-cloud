package io.ecommerce.userservice.generator;

import io.ecommerce.userservice.core.domain.entity.User;
import io.ecommerce.userservice.core.repository.UserRepo;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestConstructor;

/**
 * @author : choi-ys
 * @date : 2022/01/07 4:32 오후
 */
@Component
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserGenerator {

    private final UserRepo userRepo;

    public UserGenerator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public static final String EMAIL = "test@gmail.com";
    public static final String PASSWORD = "password";
    public static final String NAME = "choi-ys";

    public static User userMock() {
        return User.of(EMAIL, PASSWORD, NAME);
    }

    public User savedUser() {
        return userRepo.saveAndFlush(userMock());
    }
}