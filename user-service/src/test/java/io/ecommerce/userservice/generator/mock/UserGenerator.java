package io.ecommerce.userservice.generator.mock;

import io.ecommerce.userservice.core.domain.dto.response.OrderResponse;
import io.ecommerce.userservice.core.domain.dto.response.UserResponse;
import io.ecommerce.userservice.core.domain.dto.response.common.PageResponse;
import io.ecommerce.userservice.core.domain.entity.User;
import io.ecommerce.userservice.core.repository.UserRepo;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    public static UserResponse userResponse() {
        return UserResponse.to(userMock());
    }

    public User savedUser() {
        return userRepo.saveAndFlush(userMock());
    }

    public static PageResponse<OrderResponse> userOrderListResponseMock() {
        Long id = 0L;
        Long userId = 0L;
        Long productId = 0L;
        Integer quantity = 0;
        Long unitPrice = 0L;
        Long totalPrice = 0L;
        LocalDateTime createdAt = LocalDateTime.now();
        return PageResponse.of(new PageImpl<>(
                List.of(
                        OrderResponse.of(
                                id,
                                userId,
                                productId,
                                quantity,
                                unitPrice,
                                totalPrice,
                                createdAt
                        )
                )));
    }
}