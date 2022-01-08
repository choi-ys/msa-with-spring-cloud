package io.ecommerce.userservice.common.utils;

import org.springframework.data.domain.Pageable;

/**
 * @author : choi-ys
 * @date : 2022/01/08 5:55 오후
 */
public class PageUtils {

    public static Pageable pageNumberToIndex(Pageable pageable) {
        return pageable.getPageNumber() != 0 ?
                pageable.withPage(pageable.getPageNumber() - 1) : pageable;
    }
}
