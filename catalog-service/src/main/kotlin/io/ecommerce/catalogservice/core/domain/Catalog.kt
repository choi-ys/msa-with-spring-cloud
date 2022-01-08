package io.ecommerce.catalogservice.core.domain

import io.ecommerce.catalogservice.core.domain.common.Auditor
import javax.persistence.Entity
import javax.persistence.Table

/**
 * @author : choi-ys
 * @date : 2022/01/08 7:08 오후
 */
@Entity
@Table(name = "catalog")
data class Catalog(
    var name: String,
    var productId: String,
    var price: Long,
    var stock: Int,
) : Auditor()