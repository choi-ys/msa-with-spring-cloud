package io.ecommerce.orderservice.api

import io.ecommerce.orderservice.core.domain.dto.request.OrdersRequest
import io.ecommerce.orderservice.core.domain.dto.response.OrderResponse
import io.ecommerce.orderservice.core.service.OrdersService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * @author : choi-ys
 * @date : 2022/01/09 8:09 오후
 */
@RestController
@RequestMapping("orders")
class OrderController(
    private val ordersService: OrdersService,
) {

    @PostMapping
    fun creatOrder(@Valid @RequestBody ordersRequest: OrdersRequest): ResponseEntity<OrderResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(ordersService.createOrder(ordersRequest))

}