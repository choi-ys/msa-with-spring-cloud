package io.ecommerce.orderservice.core.domain.dto.request

import io.ecommerce.orderservice.core.domain.dto.entity.Orders

/**
 * @author : choi-ys
 * @date : 2022/01/09 7:53 오후
 */
data class OrdersRequest(
    val userId: Long,
    val productId: Long,
    val quantity: Int,
    val unitPrice: Long,
) {
    fun toEntity(): Orders {
        return Orders(
            userId = userId,
            productId = productId,
            quantity = quantity,
            unitPrice = unitPrice,
            totalPrice = quantity * unitPrice
        )
    }
}
