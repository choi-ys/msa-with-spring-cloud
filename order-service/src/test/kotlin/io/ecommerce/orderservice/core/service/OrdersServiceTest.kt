package io.ecommerce.orderservice.core.service

import io.ecommerce.orderservice.core.domain.dto.entity.Orders
import io.ecommerce.orderservice.core.domain.dto.request.OrdersRequest
import io.ecommerce.orderservice.core.repository.OrdersRepo
import io.ecommerce.orderservice.generator.OrdersGenerator
import org.assertj.core.api.BDDAssertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

/**
 * @author : choi-ys
 * @date : 2022/01/09 8:01 오후
 */
@ExtendWith(MockitoExtension::class)
@DisplayName("Service:Orders")
internal class OrdersServiceTest {

    @Mock
    lateinit var ordersRepo: OrdersRepo

    @InjectMocks
    lateinit var ordersService: OrdersService

    @Test
    @DisplayName("주문 생성")
    fun createOrder() {
        // Given
        val ordersMock = OrdersGenerator.ordersMock()
        val ordersRequest = OrdersRequest(
            ordersMock.userId,
            ordersMock.productId,
            ordersMock.quantity,
            ordersMock.unitPrice
        )
        given(ordersRepo.save(any(Orders::class.java))).willReturn(ordersMock)

        // When
        val expected = ordersService.createOrder(ordersRequest)

        // Then
        assertAll(
            { BDDAssertions.then(expected.userId).isEqualTo(ordersMock.userId) },
            { BDDAssertions.then(expected.productId).isEqualTo(ordersMock.productId) },
            { BDDAssertions.then(expected.quantity).isEqualTo(ordersMock.quantity) },
            { BDDAssertions.then(expected.unitPrice).isEqualTo(ordersMock.unitPrice) },
            { BDDAssertions.then(expected.totalPrice).isEqualTo(ordersMock.quantity * ordersMock.unitPrice) },
        )
        verify(ordersRepo).save(any(Orders::class.java))
    }
}