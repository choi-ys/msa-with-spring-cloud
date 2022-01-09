package io.ecommerce.orderservice.api

import io.ecommerce.orderservice.config.test.SpringBootTestSupporter
import io.ecommerce.orderservice.generator.OrdersGenerator
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author : choi-ys
 * @date : 2022/01/09 9:58 오후
 */
@SpringBootTestSupporter
@DisplayName("API:OrdersQuery")
@Import(OrdersGenerator::class)
internal class OrdersQueryControllerTest(
    private val mockMvc: MockMvc,
    private val ordersGenerator: OrdersGenerator,
) {

    companion object {
        private const val ORDERS_URL = "/orders"
    }

    @Test
    @DisplayName("[GET:200]특정 회원의 주문 조회")
    fun findByUserId() {
        // Given
        val savedOrders = ordersGenerator.savedOrders()
        val urlTemplate = String.format("%s/{id}", ORDERS_URL)

        // When
        val resultActions = this.mockMvc.perform(get(urlTemplate, savedOrders.userId)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions.andDo(MockMvcResultHandlers.print())
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
            .andExpect(jsonPath("$.embedded[*].userId").exists())
            .andExpect(jsonPath("$.embedded[*].productId").exists())
            .andExpect(jsonPath("$.embedded[*].quantity").exists())
            .andExpect(jsonPath("$.embedded[*].unitPrice").exists())
            .andExpect(jsonPath("$.embedded[*].createdAt").exists())
            .andExpect(jsonPath("$.embedded[*].updatedAt").exists())
    }
}