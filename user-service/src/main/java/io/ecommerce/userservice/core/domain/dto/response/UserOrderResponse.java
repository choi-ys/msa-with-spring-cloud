package io.ecommerce.userservice.core.domain.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.ecommerce.userservice.core.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Super;

import java.util.List;

/**
 * @author : choi-ys
 * @date : 2022/01/07 4:21 오후
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserOrderResponse extends UserResponse {

    private List<OrderResponse> orderList;

    @QueryProjection
    public UserOrderResponse(Long userId, String email, String name) {
        super(userId, email, name);
    }

    public static UserOrderResponse to(User user) {
        return new UserOrderResponse(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }

    public void setOrderList(List<OrderResponse> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "UserOrderResponse{" +
                super.toString() +
                "orderList=" + orderList +
                '}';
    }
}
