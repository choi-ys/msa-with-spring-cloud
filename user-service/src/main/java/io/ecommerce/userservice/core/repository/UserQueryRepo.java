package io.ecommerce.userservice.core.repository;

import io.ecommerce.userservice.core.domain.dto.request.UserSearchRequest;
import io.ecommerce.userservice.core.domain.dto.response.UserResponse;
import org.springframework.data.domain.Page;

/**
 * @author : choi-ys
 * @date : 2022/01/08 3:38 오후
 */
public interface UserQueryRepo {
    Page<UserResponse> searchUserPageBySearchParams(UserSearchRequest userSearchRequest);
}
