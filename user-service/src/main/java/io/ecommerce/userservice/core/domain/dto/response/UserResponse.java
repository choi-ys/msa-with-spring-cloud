package io.ecommerce.userservice.core.domain.dto.response;

import io.ecommerce.userservice.core.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : choi-ys
 * @date : 2022/01/07 4:21 오후
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

    private Long userId;
    private String email;
    private String name;

    private UserResponse(Long userId, String email, String name) {
        this.userId = userId;
        this.email = email;
        this.name = name;
    }

    public static UserResponse to(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }
}
