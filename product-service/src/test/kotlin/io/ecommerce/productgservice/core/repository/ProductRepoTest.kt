package io.ecommerce.productgservice.core.repository

import io.ecommerce.productgservice.config.test.DataJpaTestSupporter
import io.ecommerce.productgservice.core.domain.Product
import io.ecommerce.productgservice.generator.ProductGenerator
import org.assertj.core.api.BDDAssertions
import org.assertj.core.api.Java6BDDAssertions.then
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest

/**
 * @author : choi-ys
 * @date : 2022/01/08 7:15 오후
 */
@DataJpaTestSupporter
@DisplayName("Repo:Product")
@Import(ProductGenerator::class)
class ProductRepoTest(
    private val productRepo: ProductRepo,
    private val productGenerator: ProductGenerator,
) {

    @Test
    @DisplayName("상품 엔티티 저장")
    fun save() {
        // Given
        val name = "퀀텀"
        val productCode = "COIN_001"
        val price = 15000L
        val stock = 5
        val product = Product(name, productCode, price, stock)

        // When
        val expected = productRepo.save(product)

        // Then
        assertAll(
            { then(expected.id).isNotNull() },
            { then(expected.name).isEqualTo(product.name) },
            { then(expected.price).isEqualTo(product.price) },
            { then(expected.stock).isEqualTo(product.stock) },
            { then(expected.createdAt).isNotNull() },
            { then(expected.updatedAt).isNotNull() }
        )
    }

    @Test
    @DisplayName("상품 엔티티 조회")
    fun findById() {
        // Given
        val savedProduct = productGenerator.savedProduct()

        // When
        val expected = productRepo.findById(savedProduct.id).orElseThrow()

        // Then
        assertAll(
            { then(expected.id).isEqualTo(savedProduct.id) },
            { then(expected.name).isEqualTo(savedProduct.name) },
            { then(expected.price).isEqualTo(savedProduct.price) },
            { then(expected.stock).isEqualTo(savedProduct.stock) },
            { then(expected.createdAt).isEqualTo(savedProduct.createdAt) },
            { then(expected.updatedAt).isEqualTo(savedProduct.updatedAt) }
        )
    }

    @Test
    @DisplayName("상품 목록 조회")
    fun findAll() {
        // Given
        val requestPage = 0
        val perPageNum = 5
        val pageRequest = PageRequest.of(requestPage, perPageNum)
        val savedProduct = productGenerator.savedProduct()

        // When
        val expected = productRepo.findAll(pageRequest)

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
                    .filter { it.productCode == savedProduct.productCode }
                    .count()
                ).isEqualTo(1)
            },
        );
    }
}