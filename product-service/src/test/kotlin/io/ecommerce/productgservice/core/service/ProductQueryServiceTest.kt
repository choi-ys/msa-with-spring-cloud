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
}