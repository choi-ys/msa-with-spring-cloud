package io.ecommerce.orderservice.core.service

import io.ecommerce.orderservice.core.domain.dto.request.OrdersRequest
import io.ecommerce.orderservice.core.domain.dto.response.OrderResponse
import io.ecommerce.orderservice.core.repository.OrdersRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author : choi-ys
 * @date : 2022/01/09 7:51 오후
 */
@Service
@Transactional(readOnly = false)
class OrdersService(
    private val ordersRepo: OrdersRepo,
    private val orderProducer: OrderProducer,
) {

    fun createOrder(ordersRequest: OrdersRequest): OrderResponse =
        ordersRepo.save(ordersRequest.toEntity()).let {
            val orderResponse = OrderResponse.of(it)
            orderProducer.send(orderResponse)
            return orderResponse
        }
}