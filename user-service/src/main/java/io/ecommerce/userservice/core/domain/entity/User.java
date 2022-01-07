package io.ecommerce.userservice.core.domain.entity;

import io.ecommerce.userservice.core.domain.common.Auditor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author : choi-ys
 * @date : 2022/01/07 3:39 오후
 */
@Entity
@Table(
        name = "user",
        uniqueConstraints = @UniqueConstraint(
                name = "USER_EMAIL_UNIQUE",
                columnNames = "email"
        ))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Auditor {

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User of(
            String email,
            String password,
            String name
    ) {
        return new User(email, password, name);
    }
}
