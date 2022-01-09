package io.ecommerce.orderservice.core.service

import io.ecommerce.orderservice.core.domain.dto.common.PageResponse
import io.ecommerce.orderservice.core.repository.OrdersRepo
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author : choi-ys
 * @date : 2022/01/09 9:39 오후
 */
@Service
@Transactional(readOnly = true)
class OrdersQueryService(
    private val ordersRepo: OrdersRepo,
) {

    fun findByUserId(userId: Long, pageable: Pageable) =
        ordersRepo.findByUserId(userId, pageable).let {
            PageResponse.of(it)
        }
}