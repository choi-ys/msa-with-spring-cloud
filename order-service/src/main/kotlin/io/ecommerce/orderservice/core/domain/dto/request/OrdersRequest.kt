package io.ecommerce.orderservice.core.domain.dto.request

import io.ecommerce.orderservice.core.domain.dto.entity.Orders
import javax.validation.constraints.Positive

/**
 * @author : choi-ys
 * @date : 2022/01/09 7:53 오후
 */
data class OrdersRequest(
    @field:Positive(message = "주문자 정보는 필수항목 입니다.")
    val userId: Long,

    @field:Positive(message = "주문 상품 정보는 필수항목 입니다.")
    val productId: Long,

    @field:Positive(message = "주문 수량 정보는 필수 항목입니다.")
    val quantity: Int,

    @field:Positive(message = "상품 가격은 필수 항목입니다.")
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
