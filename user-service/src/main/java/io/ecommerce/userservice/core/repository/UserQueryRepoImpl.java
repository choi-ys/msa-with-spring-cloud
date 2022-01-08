package io.ecommerce.userservice.core.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import io.ecommerce.userservice.core.domain.dto.request.SearchUserRequest;
import io.ecommerce.userservice.core.domain.dto.response.QUserResponse;
import io.ecommerce.userservice.core.domain.dto.response.UserResponse;
import io.ecommerce.userservice.core.domain.entity.QUser;
import io.ecommerce.userservice.core.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @author : choi-ys
 * @date : 2022/01/08 3:41 오후
 */
public class UserQueryRepoImpl extends QuerydslRepositorySupport implements UserQueryRepo {
    QUser user = QUser.user;

    public UserQueryRepoImpl() {
        super(User.class);
    }

    @Override
    public Page<UserResponse> searchUserPageBySearchParams(SearchUserRequest searchUserRequest) {
        JPQLQuery<UserResponse> query = from(user)
                .select(new QUserResponse(
                        user.id,
                        user.email,
                        user.name
                ))
                .where(
                        likeUserEmail(searchUserRequest.getEmail()),
                        likeUserName(searchUserRequest.getName()),
                        userCreatedAtGoe(searchUserRequest.getCreatedAt()),
                        userUpdatedAtLoe(searchUserRequest.getUpdatedAt())
                );

        return new PageImpl<>(
                getQuerydsl()
                        .applyPagination(searchUserRequest.getPageable(), query)
                        .fetch(),
                searchUserRequest.getPageable(),
                query.fetchCount()
        );
    }

    private Predicate likeUserEmail(String email) {
        return StringUtils.hasText(email) ? user.email.containsIgnoreCase(email) : null;
    }

    private Predicate likeUserName(String name) {
        return StringUtils.hasText(name) ? user.name.containsIgnoreCase(name) : null;
    }

    private Predicate userCreatedAtGoe(LocalDateTime createdAt) {
        return createdAt != null ? user.createdAt.goe(createdAt) : null;
    }

    private Predicate userUpdatedAtLoe(LocalDateTime updatedAt) {
        return updatedAt != null ? user.updatedAt.loe(updatedAt) : null;
    }
}
