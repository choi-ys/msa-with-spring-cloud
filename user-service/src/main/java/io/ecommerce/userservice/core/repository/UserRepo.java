package io.ecommerce.userservice.core.repository;

import io.ecommerce.userservice.core.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : choi-ys
 * @date : 2022/01/07 3:45 오후
 */
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
