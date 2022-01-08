package io.ecommerce.catalogservice.core.repository

import io.ecommerce.catalogservice.core.domain.Catalog
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
@DisplayName("Repo:Catalog")
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CatalogRepoTest(
    private val catalogRepo: CatalogRepo,
) {

    @Test
    @DisplayName("상품 엔티티 저장")
    fun save() {
        // Given
        val name = "퀀텀"
        val productId = "COIN_001"
        val price = 15000L
        val stock = 5
        val catalog = Catalog(name, productId, price, stock)

        // When
        val expected = catalogRepo.save(catalog)

        // Then
        assertAll(
            { then(expected.id).isNotNull() },
            { then(expected.name).isEqualTo(catalog.name) },
            { then(expected.price).isEqualTo(catalog.price) },
            { then(expected.stock).isEqualTo(catalog.stock) },
            { then(expected.createdAt).isNotNull() },
            { then(expected.updatedAt).isNotNull() }
        )
    }

    private fun catalogMock(): Catalog {
        val name = "퀀텀"
        val productId = "COIN_001"
        val price = 15000L
        val stock = 5
        return Catalog(name, productId, price, stock)
    }

    private fun savedCatalog() = catalogRepo.saveAndFlush(catalogMock())
}