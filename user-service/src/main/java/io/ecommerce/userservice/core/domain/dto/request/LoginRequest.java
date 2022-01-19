package io.ecommerce.userservice.core.domain.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : choi-ys
 * @date : 2022/01/10 5:27 오후
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
