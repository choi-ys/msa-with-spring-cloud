package io.ecommerce.productgservice.generator.mock

import io.ecommerce.productgservice.core.domain.Product
import io.ecommerce.productgservice.core.repository.ProductRepo
import org.springframework.boot.test.context.TestComponent
import org.springframework.test.context.TestConstructor

/**
 * @author : choi-ys
 * @date : 2022/01/09 4:08 오후
 */
@TestComponent
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProductGenerator(
    private val productRepo: ProductRepo,
) {

    companion object {
        fun productMock(): Product {
            val name = "퀀텀"
            val productCode = "COIN_001"
            val price = 15000L
            val stock = 5
            return Product(name, productCode, price, stock)
        }
    }

    fun savedProduct() = productRepo.saveAndFlush(productMock())

}