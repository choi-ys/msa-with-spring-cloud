package io.ecommerce.productgservice.core.domain.vo

import java.time.LocalDateTime

/**
 * @author : choi-ys
 * @date : 2022/01/13 5:25 오후
 */
data class OrderVo(
    val id: Long,
    val userId: Long,
    val productId: Long,
    val quantity: Int,
    val unitPrice: Long,
    val totalPrice: Long,
    val createdAt: LocalDateTime,
)
