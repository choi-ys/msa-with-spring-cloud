package io.ecommerce.productgservice.api

import io.ecommerce.productgservice.config.test.SpringBootTestSupporter
import io.ecommerce.productgservice.generator.ProductGenerator
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author : choi-ys
 * @date : 2022/01/09 6:17 오후
 */
@SpringBootTestSupporter
@DisplayName("API:ProductQuery")
@Import(ProductGenerator::class)
internal class ProductQueryControllerTest(
    private val mockMvc: MockMvc,
    private val productGenerator: ProductGenerator,
) {
    companion object {
        private const val PRODUCT_URL = "/product"
    }

    @Test
    @DisplayName("[GET:200]특정 상품 조회")
    fun findById() {
        // Given
        val savedProduct = productGenerator.savedProduct()
        val urlTemplate = String.format("%s/{id}", PRODUCT_URL)

        // When
        val resultActions = this.mockMvc.perform(get(urlTemplate, savedProduct.id)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions.andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.productId").value(savedProduct.id))
            .andExpect(jsonPath("$.name").value(savedProduct.name))
            .andExpect(jsonPath("$.productCode").value(savedProduct.productCode))
            .andExpect(jsonPath("$.price").value(savedProduct.price))
            .andExpect(jsonPath("$.stock").value(savedProduct.stock))
    }

    @Test
    @DisplayName("[GET:200]상품 목록 조회")
    fun findAllByPageRequest() {
        // Given
        productGenerator.savedProduct()

        // When
        val resultActions = this.mockMvc.perform(get(PRODUCT_URL)
            .param("page", "0")
            .param("size", "5")
            .param("sort", "createdAt")
            .param("direction", Sort.Direction.DESC.name)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions.andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.totalPage").exists())
            .andExpect(jsonPath("$.totalElementCount").exists())
            .andExpect(jsonPath("$.currentPage").exists())
            .andExpect(jsonPath("$.currentPageElementCount").exists())
            .andExpect(jsonPath("$.perPageNumber").exists())
            .andExpect(jsonPath("$.firstPage").exists())
            .andExpect(jsonPath("$.lastPage").exists())
            .andExpect(jsonPath("$.hasNextPage").exists())
            .andExpect(jsonPath("$.hasPrevious").exists())
            .andExpect(jsonPath("$.embedded[*].id").exists())
            .andExpect(jsonPath("$.embedded[*].name").exists())
            .andExpect(jsonPath("$.embedded[*].productCode").exists())
            .andExpect(jsonPath("$.embedded[*].price").exists())
            .andExpect(jsonPath("$.embedded[*].stock").exists())
    }
}