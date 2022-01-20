package io.ecommerce.productgservice.core.service

import io.ecommerce.productgservice.core.domain.common.PageResponse
import io.ecommerce.productgservice.core.domain.dto.response.ProductResponse
import io.ecommerce.productgservice.core.repository.ProductRepo
import io.ecommerce.productgservice.error.exception.ResourceNotFoundException
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
        productRepo.findById(id).orElseThrow {
            throw ResourceNotFoundException()
        }.let {
            ProductResponse.of(it)
        }

    fun findAllByPageRequest(pageable: Pageable) =
        productRepo.findAll(pageable).let {
            PageResponse.of(it)
        }

}