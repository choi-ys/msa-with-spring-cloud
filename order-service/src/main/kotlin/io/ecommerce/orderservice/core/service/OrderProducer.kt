package io.ecommerce.orderservice.core.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

/**
 * @author : choi-ys
 * @date : 2022/01/13 2:22 오후
 */

private val logger = LoggerFactory.getLogger(OrderProducer::class.java)

@Service
class OrderProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) {

    @Value("\${topic.order-topic}")
    val orderTopic: String? = null

    fun <T> send(payload: T) {
        kafkaTemplate.send(
            orderTopic!!,
            objectMapper.writeValueAsString(payload)
        )
        logger.info("[ORDER-PRODUCER][{}]order produce order message send from order micro-service", orderTopic)
    }
}