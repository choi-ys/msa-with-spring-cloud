package io.ecommerce.productgservice.api

import io.ecommerce.productgservice.common.utils.PageUtils
import io.ecommerce.productgservice.core.domain.Product
import io.ecommerce.productgservice.core.domain.common.PageResponse
import io.ecommerce.productgservice.core.domain.dto.response.ProductResponse
import io.ecommerce.productgservice.core.service.ProductQueryService
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
 * @date : 2022/01/09 6:04 오후
 */
@RestController
@RequestMapping("product")
class ProductQueryController(
    private val productQueryService: ProductQueryService,
) {

    @GetMapping("{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<ProductResponse> =
        ResponseEntity.ok(productQueryService.findById(id))

    @GetMapping
    fun findAllByPageRequest(
        @PageableDefault(
            page = 0,
            size = 30,
            sort = ["id"],
            direction = Sort.Direction.DESC
        ) pageable: Pageable,
    ): ResponseEntity<PageResponse<Product>> =
        ResponseEntity.ok(productQueryService.findAllByPageRequest(PageUtils.pageNumberToIndex(pageable)))
}