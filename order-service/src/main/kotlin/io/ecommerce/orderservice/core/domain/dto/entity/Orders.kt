package io.ecommerce.orderservice.core.domain.dto.entity

import io.ecommerce.orderservice.core.domain.dto.common.Auditor
import javax.persistence.Entity
import javax.persistence.Table

/**
 * @author : choi-ys
 * @date : 2022/01/09 7:21 오후
 */
@Entity
@Table(name = "orders")
data class Orders(
    var userId: Long,
    var productId: Long,
    var quantity: Int,
    var unitPrice: Long,
    var totalPrice: Long,
) : Auditor()
