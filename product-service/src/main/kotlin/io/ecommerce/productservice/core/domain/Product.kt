package io.ecommerce.productservice.core.domain

import io.ecommerce.productservice.core.domain.common.Auditor
import javax.persistence.Entity
import javax.persistence.Table

/**
 * @author : choi-ys
 * @date : 2022/01/08 7:08 오후
 */
@Entity
@Table(name = "product")
data class Product(
    var name: String,
    var productCode: String,
    var price: Long,
    var stock: Int,
) : Auditor()