package io.ecommerce.orderservice.api

import com.fasterxml.jackson.databind.ObjectMapper
import io.ecommerce.orderservice.config.test.SpringBootTestSupporter
import io.ecommerce.orderservice.core.domain.dto.request.OrdersRequest
import io.ecommerce.orderservice.error.ErrorCode
import io.ecommerce.orderservice.generator.OrdersGenerator
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author : choi-ys
 * @date : 2022/01/09 8:45 오후
 */
@SpringBootTestSupporter
@DisplayName("API:Orders")
internal class OrderControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
) {

    companion object {
        const val ORDERS_URL = "/orders"
    }

    @Test
    @DisplayName("[201:POST]")
    fun createOrder() {
        // Given
        val ordersMock = OrdersGenerator.ordersMock()
        val ordersRequest = OrdersRequest(
            ordersMock.userId,
            ordersMock.productId,
            ordersMock.quantity,
            ordersMock.unitPrice
        )

        // When
        val resultActions = this.mockMvc.perform(post(ORDERS_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsBytes(ordersRequest))
        )

        // Then
        resultActions.andDo(print())
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.userId").value(ordersRequest.userId))
            .andExpect(jsonPath("$.productId").value(ordersRequest.productId))
            .andExpect(jsonPath("$.quantity").value(ordersRequest.quantity))
            .andExpect(jsonPath("$.unitPrice").value(ordersRequest.unitPrice))
            .andExpect(jsonPath("$.totalPrice").value(ordersRequest.quantity * ordersRequest.unitPrice))
            .andExpect(jsonPath("$.createdAt").exists())
    }

    @Test
    @DisplayName("[400:POST]주문 생성 실패: 값이 잘못된 요청")
    fun createOrder_fail_cause_InValidRequest() {
        // Given
        val ordersRequest = OrdersRequest(
            0L,
            0L,
            0,
            0,
        )

        // When
        val resultActions = this.mockMvc.perform(post(ORDERS_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsBytes(ordersRequest))
        )

        // Then
        resultActions.andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("timestamp").exists())
            .andExpect(jsonPath("method").value(HttpMethod.POST.name))
            .andExpect(jsonPath("path").value(ORDERS_URL))
            .andExpect(jsonPath("code").value(ErrorCode.METHOD_ARGUMENT_NOT_VALID.code))
            .andExpect(jsonPath("message").value(ErrorCode.METHOD_ARGUMENT_NOT_VALID.message))
            .andExpect(jsonPath("$.errorDetails.[0].object").value("ordersRequest"))
            .andExpect(jsonPath("$.errorDetails.[0].field").exists())
            .andExpect(jsonPath("$.errorDetails.[0].code").value("Positive"))
            .andExpect(jsonPath("$.errorDetails.[0].rejectMessage").exists())
            .andExpect(jsonPath("$.errorDetails.[0].rejectedValue").value(0L))
    }

    @Test
    @DisplayName("[400:POST]주문 생성 실패: 값이 없는 요청")
    fun createOrder_fail_cause_emptyRequest() {
        // When
        val resultActions = this.mockMvc.perform(post(ORDERS_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions.andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("timestamp").exists())
            .andExpect(jsonPath("method").value(HttpMethod.POST.name))
            .andExpect(jsonPath("path").value(ORDERS_URL))
            .andExpect(jsonPath("code").value(ErrorCode.HTTP_MESSAGE_NOT_READABLE.code))
            .andExpect(jsonPath("message").value(ErrorCode.HTTP_MESSAGE_NOT_READABLE.message))
            .andExpect(jsonPath("$.errorDetails").isEmpty)
    }
}