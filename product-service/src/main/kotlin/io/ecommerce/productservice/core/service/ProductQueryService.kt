package io.ecommerce.productservice.core.service

import io.ecommerce.productservice.core.domain.common.PageResponse
import io.ecommerce.productservice.core.domain.dto.response.ProductResponse
import io.ecommerce.productservice.core.repository.ProductRepo
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author : choi-ys
 * @date : 2022/01/09 4:05 오후
 */
@Service
@Transactional(readOnly = true)
class ProductQueryService(
    private val productRepo: ProductRepo,
) {

    fun findById(id: Long): ProductResponse =
        productRepo.findById(id).orElseThrow().let {
            ProductResponse.of(it)
        }

    fun findAllByPageRequest(pageable: Pageable) =
        productRepo.findAll(pageable).let {
            PageResponse.of(it)
        }

}