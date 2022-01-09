package io.ecommerce.productgservice.core.repository

import io.ecommerce.productgservice.core.domain.Product
import org.assertj.core.api.Java6BDDAssertions.then
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

/**
 * @author : choi-ys
 * @date : 2022/01/08 7:15 오후
 */
@DataJpaTest
@DisplayName("Repo:Product")
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProductRepoTest(
    private val productRepo: ProductRepo,
) {

    @Test
    @DisplayName("상품 엔티티 저장")
    fun save() {
        // Given
        val name = "퀀텀"
        val productId = "COIN_001"
        val price = 15000L
        val stock = 5
        val product = Product(name, productId, price, stock)

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

    private fun productMock(): Product {
        val name = "퀀텀"
        val productId = "COIN_001"
        val price = 15000L
        val stock = 5
        return Product(name, productId, price, stock)
    }

    private fun savedProduct() = productRepo.saveAndFlush(productMock())
}