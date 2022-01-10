package io.ecommerce.userservice.core.repository;

import io.ecommerce.userservice.core.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : choi-ys
 * @date : 2022/01/07 3:45 오후
 */
public interface UserRepo extends JpaRepository<User, Long>, UserQueryRepo {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
