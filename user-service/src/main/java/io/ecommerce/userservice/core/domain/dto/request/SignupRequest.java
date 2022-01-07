package io.ecommerce.userservice.core.domain.dto.request;

import io.ecommerce.userservice.core.domain.dto.request.validator.EmailUnique;
import io.ecommerce.userservice.core.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author : choi-ys
 * @date : 2022/01/07 3:38 오후
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupRequest {

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "이메일 형식에 맞게 입력하세요")
    @EmailUnique
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 항목입니다.")
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
