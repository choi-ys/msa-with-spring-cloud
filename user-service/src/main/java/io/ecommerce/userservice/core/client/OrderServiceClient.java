package io.ecommerce.userservice.core.client;

import io.ecommerce.userservice.core.domain.dto.response.OrderResponse;
import io.ecommerce.userservice.core.domain.dto.response.common.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author : choi-ys
 * @date : 2022/01/17 10:45 오전
 */
@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @GetMapping(value = "/orders/{userId}")
    PageResponse<OrderResponse> getOrders(@PathVariable Long userId);
}