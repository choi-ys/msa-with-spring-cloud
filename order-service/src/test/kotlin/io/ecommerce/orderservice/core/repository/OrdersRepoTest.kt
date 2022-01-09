package io.ecommerce.orderservice.core.repository

import io.ecommerce.orderservice.core.domain.dto.entity.Orders
import io.ecommerce.orderservice.config.test.DataJpaTestSupporter
import org.assertj.core.api.BDDAssertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.data.domain.PageRequest

/**
 * @author : choi-ys
 * @date : 2022/01/09 7:33 오후
 */
@DataJpaTestSupporter
@DisplayName("Repo:Order")
internal class OrdersRepoTest(
    private val ordersRepo: OrdersRepo,
) {

    @Test
    @DisplayName("주문 엔티티 저장")
    fun save() {
        // Given
        val userId: Long = 1L
        val productId: Long = 1L
        val quantity: Int = 1
        val unitPrice = 15000L
        val totalPrice = quantity * unitPrice;

        val order = Orders(userId, productId, quantity, unitPrice, totalPrice)

        // When
        val expected = ordersRepo.save(order)

        // Then
        assertAll(
            { BDDAssertions.then(expected.id).isNotNull() },
            { BDDAssertions.then(expected.userId).isEqualTo(order.userId) },
            { BDDAssertions.then(expected.productId).isEqualTo(order.productId) },
            { BDDAssertions.then(expected.quantity).isEqualTo(order.quantity) },
            { BDDAssertions.then(expected.unitPrice).isEqualTo(order.unitPrice) },
            { BDDAssertions.then(expected.totalPrice).isEqualTo(order.totalPrice) },
            { BDDAssertions.then(expected.createdAt).isNotNull() },
            { BDDAssertions.then(expected.updatedAt).isNotNull() }
        )
    }

    @Test
    @DisplayName("특정 회원의 주문 목록 조회")
    fun findByUserId() {
        // Given
        val requestPage = 0
        val perPageNum = 5
        val pageRequest = PageRequest.of(requestPage, perPageNum)
        val savedOrders = savedOrders()

        // When
        val expected = ordersRepo.findByUserId(savedOrders.userId, pageRequest)

        // Then
        assertAll(
            { BDDAssertions.then(expected.totalPages).isEqualTo(1) },
            { BDDAssertions.then(expected.totalElements).isEqualTo(1) },
            { BDDAssertions.then(expected.number).isEqualTo(requestPage) },
            { BDDAssertions.then(expected.numberOfElements).isEqualTo(1) },
            { BDDAssertions.then(expected.size).isEqualTo(perPageNum) },
            { BDDAssertions.then(expected.isFirst).isTrue() },
            { BDDAssertions.then(expected.isLast).isTrue() },
            { BDDAssertions.then(expected.hasNext()).isFalse() },
            { BDDAssertions.then(expected.hasPrevious()).isFalse() },
            {
                BDDAssertions.then(expected.content.stream()
                    .filter { it.userId == savedOrders.userId }
                    .count()
                ).isEqualTo(1)
            },
        )
    }

    fun ordersMock(): Orders {
        val userId: Long = 1L
        val productId: Long = 1L
        val quantity: Int = 1
        val unitPrice = 15000L
        val totalPrice = quantity * unitPrice;

        return Orders(userId, productId, quantity, unitPrice, totalPrice)
    }

    fun savedOrders(): Orders =
        ordersRepo.save(ordersMock())
}