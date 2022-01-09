package io.ecommerce.orderservice.core.repository

import io.ecommerce.orderservice.core.domain.dto.entity.Orders
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author : choi-ys
 * @date : 2022/01/09 7:21 오후
 */
interface OrdersRepo : JpaRepository<Orders, Long> {
    fun findByUserId(userId: Long, pageable: Pageable): Page<Orders>
}