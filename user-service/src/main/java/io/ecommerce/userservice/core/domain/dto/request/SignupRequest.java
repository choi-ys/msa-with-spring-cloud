package io.ecommerce.userservice.core.domain.dto.request;

import io.ecommerce.userservice.core.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : choi-ys
 * @date : 2022/01/07 3:38 오후
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupRequest {
    private String email;
    private String password;
    private String name;

    public SignupRequest(
            String email,
            String password,
            String name
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.of(email, passwordEncoder.encode(password), name);
    }
}
