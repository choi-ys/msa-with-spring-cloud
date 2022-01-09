package io.ecommerce.orderservice.api

import io.ecommerce.orderservice.common.utils.PageUtils
import io.ecommerce.orderservice.core.domain.dto.common.PageResponse
import io.ecommerce.orderservice.core.domain.dto.entity.Orders
import io.ecommerce.orderservice.core.service.OrdersQueryService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author : choi-ys
 * @date : 2022/01/09 9:55 오후
 */
@RestController
@RequestMapping("orders")
class OrdersQueryController(
    private val ordersQueryService: OrdersQueryService,
) {
    @GetMapping("{userId}")
    fun findByUserId(
        @PathVariable("userId") userId: Long,
        @PageableDefault(
            page = 0,
            size = 30,
            sort = ["id"],
            direction = Sort.Direction.DESC
        ) pageable: Pageable,
    ): ResponseEntity<PageResponse<Orders>> =
        ResponseEntity.ok(ordersQueryService.findByUserId(userId, PageUtils.pageNumberToIndex(pageable)))

}