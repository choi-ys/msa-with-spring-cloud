package io.ecommerce.productservice.core.repository

import io.ecommerce.productservice.core.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author : choi-ys
 * @date : 2022/01/08 7:15 오후
 */
interface ProductRepo : JpaRepository<Product, Long> {
}