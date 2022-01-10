package io.ecommerce.productgservice.core.domain.dto.response

import io.ecommerce.productgservice.core.domain.Product

/**
 * @author : choi-ys
 * @date : 2022/01/09 3:53 오후
 */
data class ProductResponse(
    val productId: Long,
    val name: String,
    val productCode: String,
    val price: Long,
    val stock: Int,
) {
    companion object {
        fun of(product: Product): ProductResponse {
            return ProductResponse(
                product.id,
                product.name,
                product.productCode,
                product.price,
                product.stock
            )
        }
    }
}
