package io.ecommerce.productgservice.core.service

import io.ecommerce.productgservice.core.repository.ProductRepo
import io.ecommerce.productgservice.generator.ProductGenerator
import org.assertj.core.api.BDDAssertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*

/**
 * @author : choi-ys
 * @date : 2022/01/09 4:07 오후
 */
@ExtendWith(MockitoExtension::class)
@DisplayName("Service:Product")
internal class ProductQueryServiceTest {

    @Mock
    lateinit var productRepo: ProductRepo

    @InjectMocks
    lateinit var productQueryService: ProductQueryService

    @Test
    @DisplayName("특정 상품 조회")
    fun findById() {
        // Given
        val productMock = ProductGenerator.productMock()
        given(productRepo.findById(anyLong())).willReturn(Optional.of(productMock))

        // When
        val expected = productQueryService.findById(anyLong())

        // Then
        assertAll(
            { BDDAssertions.then(expected.name).isEqualTo(productMock.name) },
            { BDDAssertions.then(expected.productCode).isEqualTo(productMock.productCode) },
            { BDDAssertions.then(expected.price).isEqualTo(productMock.price) },
            { BDDAssertions.then(expected.stock).isEqualTo(productMock.stock) },
        )
        verify(productRepo).findById(anyLong())
    }

    @Test
    @DisplayName("상품 목록 조회")
    fun findAll() {
        // Given
        val requestPage = 0
        val perPageNum = 20
        val pageRequest = PageRequest.of(requestPage, perPageNum)
        val productMock = ProductGenerator.productMock()
        given(productRepo.findAll(any(PageRequest::class.java))).willReturn(
            PageImpl(listOf(productMock))
        )

        // When
        val expected = productQueryService.findAllByPageRequest(pageRequest)
        expected.perPageNumber

        // Then
        assertAll(
            { BDDAssertions.then(expected.totalPage).isEqualTo(1) },
            { BDDAssertions.then(expected.totalElementCount).isEqualTo(1) },
            { BDDAssertions.then(expected.currentPage).isEqualTo(requestPage.plus(1)) },
            { BDDAssertions.then(expected.currentPageElementCount).isEqualTo(1) },
            { BDDAssertions.then(expected.firstPage).isTrue() },
            { BDDAssertions.then(expected.lastPage).isTrue() },
            { BDDAssertions.then(expected.hasNextPage).isFalse() },
            { BDDAssertions.then(expected.hasPrevious).isFalse() },
            {
                BDDAssertions.then(expected.embedded.stream()
                    .filter { it.productCode == productMock.productCode }
                    .count()).isEqualTo(1)
            },

            )
    }
}