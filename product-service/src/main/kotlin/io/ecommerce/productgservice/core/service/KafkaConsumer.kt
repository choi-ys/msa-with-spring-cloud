package io.ecommerce.productgservice.core.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.ecommerce.productgservice.core.domain.vo.OrderVo
import io.ecommerce.productgservice.core.repository.ProductRepo
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author : choi-ys
 * @date : 2022/01/13 1:47 오후
 */

private val logger = LoggerFactory.getLogger(KafkaConsumer::class.java)

@Service
class KafkaConsumer(
    private val productRepo: ProductRepo,
    private val objectMapper: ObjectMapper,
) {

    @Transactional
    @KafkaListener(topics = ["order-topic"])
    open fun processMessage(consumerRecord: ConsumerRecord<String, String>) {
        logger.info("[key:{}][offset: {}][partition: {}]" +
                "[timestamp: {}][topic: {}][leaderEpoch: {}]" +
                "[serializedKeySize: {}][serializedValueSize: {}][value: {}]",
            consumerRecord.key(), consumerRecord.offset(), consumerRecord.partition(),
            consumerRecord.timestamp(), consumerRecord.topic(), consumerRecord.leaderEpoch(),
            consumerRecord.serializedKeySize(), consumerRecord.serializedValueSize(), consumerRecord.value()
        )

        val orderVo = objectMapper.readValue(consumerRecord.value(), OrderVo::class.java)

        productRepo.findById(orderVo.productId).orElseThrow {
            throw IllegalArgumentException("존재하지 않는 상품입니다.")
        }.let { product ->
            product.order(orderVo.quantity)
            productRepo.save(product)
        }
    }

}