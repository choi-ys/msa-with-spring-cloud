package io.ecommerce.orderservice.generator

import io.ecommerce.orderservice.core.domain.dto.entity.Orders
import io.ecommerce.orderservice.core.repository.OrdersRepo
import org.springframework.boot.test.context.TestComponent
import org.springframework.test.context.TestConstructor

/**
 * @author : choi-ys
 * @date : 2022/01/09 8:03 오후
 */
@TestComponent
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class OrdersGenerator(
    private val ordersRepo: OrdersRepo,
) {
    companion object {
        fun ordersMock(): Orders {
            val userId: Long = 1L
            val productId: Long = 1L
            val quantity: Int = 1
            val unitPrice = 15000L
            val totalPrice = quantity * unitPrice;

            return Orders(userId, productId, quantity, unitPrice, totalPrice)
        }
    }

    fun savedOrders(): Orders =
        ordersRepo.save(ordersMock())
}