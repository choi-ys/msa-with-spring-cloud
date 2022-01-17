package io.ecommerce.userservice.core.domain.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : choi-ys
 * @date : 2022/01/17 11:12 오전
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponse {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private Long unitPrice;
    private Long totalPrice;
    private LocalDateTime createdAt;

    private OrderResponse(
            Long id,
            Long userId,
            Long productId,
            Integer quantity,
            Long unitPrice,
            Long totalPrice,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public static OrderResponse of(
            Long id,
            Long userId,
            Long productId,
            Integer quantity,
            Long unitPrice,
            Long totalPrice,
            LocalDateTime createdAt
    ) {
        return new OrderResponse(
                id,
                userId,
                productId,
                quantity,
                unitPrice,
                totalPrice,
                createdAt
        );
    }
}
