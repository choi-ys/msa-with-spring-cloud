package io.ecommerce.userservice.core.domain.dto.response.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author : choi-ys
 * @date : 2022/01/08 5:13 오후
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResponse<T> {
    private Integer totalPages;
    private Long totalElementCount;
    private Integer currentPage;
    private Integer currentElementCount;
    private Integer perPageNumber;

    private Boolean firstPage;
    private Boolean lastPage;
    private Boolean hasNextPage;
    private Boolean hasPrevious;

    private List<T> embedded;

    private PageResponse(
            Integer totalPages,
            Long totalElementCount,
            Integer currentPage,
            Integer currentElementCount,
            Integer perPageNumber,
            Boolean firstPage,
            Boolean lastPage,
            Boolean hasNextPage,
            Boolean hasPrevious,
            List<T> embedded
    ) {
        this.totalPages = totalPages;
        this.totalElementCount = totalElementCount;
        this.currentPage = currentPage;
        this.currentElementCount = currentElementCount;
        this.perPageNumber = perPageNumber;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.hasNextPage = hasNextPage;
        this.hasPrevious = hasPrevious;
        this.embedded = embedded;
    }

    public static <T> PageResponse of(Page<T> page) {
        return new PageResponse(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getNumberOfElements(),
                page.getSize(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious(),
                page.getContent()
        );
    }
}
