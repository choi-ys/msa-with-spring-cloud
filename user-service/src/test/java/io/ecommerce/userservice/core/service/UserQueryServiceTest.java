package io.ecommerce.userservice.core.service;

import io.ecommerce.userservice.core.domain.dto.response.UserResponse;
import io.ecommerce.userservice.core.domain.entity.User;
import io.ecommerce.userservice.core.repository.UserRepo;
import io.ecommerce.userservice.generator.UserGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertAll;
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

    @InjectMocks
    private UserQueryService userQueryService;

    @Test
    @DisplayName("특정 회원 조회")
    public void findById() {
        // Given
        User user = UserGenerator.userMock();
        given(userRepo.findById(anyLong())).willReturn(Optional.of(user));

        // When
        UserResponse expected = userQueryService.findById(anyLong());

        // Then
        assertAll(
                () -> then(expected.getEmail()).isEqualTo(user.getEmail()),
                () -> then(expected.getName()).isEqualTo(user.getName())
        );
        verify(userRepo).findById(anyLong());
    }
}