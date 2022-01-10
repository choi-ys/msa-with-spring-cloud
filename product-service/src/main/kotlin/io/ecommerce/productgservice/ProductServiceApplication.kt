package io.ecommerce.productgservice

import io.ecommerce.productgservice.core.domain.Product
import io.ecommerce.productgservice.core.repository.ProductRepo
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class ProductServiceApplication(
    private val productRepo: ProductRepo,
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        productRepo.saveAll(listOf(
            Product("퀀텀", "COIN_001", 13000L, 40),
            Product("페이코인", "COIN_002", 1500L, 150),
            Product("리플", "COIN_003", 1300L, 80),
            Product("던프로토콜", "COIN_004", 6000L, 30),
        ))
    }
}

fun main(args: Array<String>) {
    runApplication<ProductServiceApplication>(*args)
}
