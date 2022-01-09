package io.ecommerce.orderservice.core.domain.dto.response

import io.ecommerce.orderservice.core.domain.dto.entity.Orders
import java.time.LocalDateTime

/**
 * @author : choi-ys
 * @date : 2022/01/09 7:57 오후
 */
data class OrderResponse(
    val id: Long,
    val userId: Long,
    val productId: Long,
    val quantity: Int,
    val unitPrice: Long,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun of(orders: Orders): OrderResponse {
            return OrderResponse(
                id = orders.id,
                userId = orders.userId,
                productId = orders.productId,
                quantity = orders.quantity,
                unitPrice = orders.unitPrice,
                createdAt = orders.createdAt.let { LocalDateTime.now() }
            )
        }
    }
}
