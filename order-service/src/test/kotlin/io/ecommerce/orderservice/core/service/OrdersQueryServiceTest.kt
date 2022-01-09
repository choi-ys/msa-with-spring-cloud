package io.ecommerce.orderservice.core.service

import io.ecommerce.orderservice.core.repository.OrdersRepo
import io.ecommerce.orderservice.generator.OrdersGenerator
import org.assertj.core.api.BDDAssertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

/**
 * @author : choi-ys
 * @date : 2022/01/09 9:43 오후
 */
@ExtendWith(MockitoExtension::class)
@DisplayName("Service:Orders")
internal class OrdersQueryServiceTest {

    @Mock
    lateinit var ordersRepo: OrdersRepo

    @InjectMocks
    lateinit var ordersQueryService: OrdersQueryService

    @Test
    @DisplayName("특정 사용자의 주문 목록 조회")
    fun findByUserId() {
        // Given
        val requestPage = 0
        val perPageNum = 20
        val pageRequest = PageRequest.of(requestPage, perPageNum)
        val ordersMock = OrdersGenerator.ordersMock()
        given(ordersRepo.findByUserId(ordersMock.userId, pageRequest))
            .willReturn(PageImpl(listOf(ordersMock), pageRequest, 1L))

        // When
        val expected = ordersQueryService.findByUserId(ordersMock.userId, pageRequest)

        // Then
        assertAll(
            { BDDAssertions.then(expected.totalPage).isEqualTo(1) },
            { BDDAssertions.then(expected.totalElementCount).isEqualTo(1) },
            { BDDAssertions.then(expected.currentPage).isEqualTo(requestPage.plus(1)) },
            { BDDAssertions.then(expected.currentPageElementCount).isEqualTo(1) },
            { BDDAssertions.then(expected.perPageNumber).isEqualTo(perPageNum) },
            { BDDAssertions.then(expected.firstPage).isTrue() },
            { BDDAssertions.then(expected.lastPage).isTrue() },
            { BDDAssertions.then(expected.hasNextPage).isFalse() },
            { BDDAssertions.then(expected.hasPrevious).isFalse() },
            {
                BDDAssertions.then(expected.embedded.stream()
                    .filter { it.userId == ordersMock.userId }
                    .count()).isEqualTo(1)
            },
        )
        verify(ordersRepo).findByUserId(ordersMock.userId, pageRequest)
    }
}