package io.ecommerce.userservice.core.service;

import io.ecommerce.userservice.core.client.OrderServiceClient;
import io.ecommerce.userservice.core.domain.dto.request.UserSearchRequest;
import io.ecommerce.userservice.core.domain.dto.response.OrderResponse;
import io.ecommerce.userservice.core.domain.dto.response.UserResponse;
import io.ecommerce.userservice.core.domain.dto.response.common.PageResponse;
import io.ecommerce.userservice.core.domain.entity.User;
import io.ecommerce.userservice.core.repository.UserRepo;
import io.ecommerce.userservice.generator.UserGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author : choi-ys
 * @date : 2022/01/08 2:29 오후
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Service:UserQuery")
class UserQueryServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private OrderServiceClient orderServiceClient;

    @InjectMocks
    private UserQueryService userQueryService;

    @Test
    @DisplayName("특정 회원 조회")
    public void findById() {
        // Given
        User user = UserGenerator.userMock();
        given(userRepo.findById(anyLong())).willReturn(Optional.of(user));

        given(orderServiceClient.getOrders(anyLong())).willReturn(UserGenerator.userOrderListResponseMock());

        // When
        UserResponse expected = userQueryService.findById(anyLong());

        // Then
        assertAll(
                () -> then(expected.getEmail()).isEqualTo(user.getEmail()),
                () -> then(expected.getName()).isEqualTo(user.getName())
        );
        verify(userRepo).findById(anyLong());
        verify(orderServiceClient).getOrders(anyLong());
    }

    @Test
    @DisplayName("회원 검색")
    public void userSearch() {
        // Given
        User user = UserGenerator.userMock();
        UserSearchRequest userSearchRequest = UserSearchRequest.of(
                user.getEmail(),
                null,
                null,
                null,
                PageRequest.of(0, 5)
        );
        given(userRepo.searchUserPageBySearchParams(any(UserSearchRequest.class)))
                .willReturn(new PageImpl<>(Arrays.asList(UserGenerator.userResponse())));

        // When
        PageResponse<UserResponse> expected = userQueryService.userSearch(userSearchRequest);

        // Then
        assertThat(expected.getEmbedded())
                .allSatisfy(userSearchResponse -> {
                    assertTrue(userSearchResponse.getEmail().contains(user.getEmail()));
                    assertTrue(userSearchResponse.getName().contains(user.getName()));
                });
        verify(userRepo).searchUserPageBySearchParams(userSearchRequest);
    }
}