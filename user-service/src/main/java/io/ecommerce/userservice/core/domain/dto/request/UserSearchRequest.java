package io.ecommerce.userservice.core.domain.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * @author : choi-ys
 * @date : 2022/01/08 3:39 오후
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSearchRequest {
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Pageable pageable;

    private UserSearchRequest(
            final String email,
            final String name,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final Pageable pageable
    ) {
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.pageable = pageable;
    }

    public static UserSearchRequest of(
            final String email,
            final String name,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final Pageable pageable
    ) {
        return new UserSearchRequest(email, name, createdAt, updatedAt, pageable);
    }
}
