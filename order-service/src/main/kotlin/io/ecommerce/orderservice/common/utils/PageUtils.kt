package io.ecommerce.orderservice.common.utils

import org.springframework.data.domain.Pageable

/**
 * @author : choi-ys
 * @date : 2022/01/09 6:14 오후
 */
class PageUtils {
    companion object {
        fun pageNumberToIndex(pageable: Pageable): Pageable {
            return if (pageable.pageNumber != 0)
                pageable.withPage(pageable.pageNumber - 1)
            else pageable
        }
    }
}