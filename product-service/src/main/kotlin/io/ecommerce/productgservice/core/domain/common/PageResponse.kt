package io.ecommerce.productgservice.core.domain.common

import org.springframework.data.domain.Page

/**
 * @author : choi-ys
 * @date : 2022/01/09 3:48 오후
 */
data class PageResponse<T>(
    val totalPage: Int,
    val totalElementCount: Long,
    val currentPage: Int,
    val currentPageElementCount: Int,
    val perPageNumber: Int,
    val firstPage: Boolean,
    val lastPage: Boolean,
    val hasNextPage: Boolean,
    val hasPrevious: Boolean,
    val embedded: List<T>,
) {
    companion object {
        fun <T> of(page: Page<T>): PageResponse<T> {
            return PageResponse(
                totalPage = page.totalPages,
                totalElementCount = page.totalElements,
                currentPage = page.number + 1,
                currentPageElementCount = page.numberOfElements,
                perPageNumber = page.size,
                firstPage = page.isFirst,
                lastPage = page.isLast,
                hasNextPage = page.hasNext(),
                hasPrevious = page.hasPrevious(),
                embedded = page.content
            )
        }
    }
}
