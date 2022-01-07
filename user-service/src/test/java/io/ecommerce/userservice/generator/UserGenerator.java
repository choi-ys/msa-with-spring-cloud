package io.ecommerce.userservice.generator;

import io.ecommerce.userservice.core.domain.entity.User;

/**
 * @author : choi-ys
 * @date : 2022/01/07 4:32 오후
 */
public class UserGenerator {

    public static final String EMAIL = "test@gmail.com";
    public static final String PASSWORD = "password";
    public static final String NAME = "choi-ys";

    public static User userMock() {
        return User.of(EMAIL, PASSWORD, NAME);
    }
}