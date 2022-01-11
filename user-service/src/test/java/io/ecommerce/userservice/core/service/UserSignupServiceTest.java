package io.ecommerce.userservice.core.service;

import io.ecommerce.userservice.core.domain.dto.request.SignupRequest;
import io.ecommerce.userservice.core.domain.dto.response.UserResponse;
import io.ecommerce.userservice.core.domain.entity.User;
import io.ecommerce.userservice.core.repository.UserRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static io.ecommerce.userservice.generator.UserGenerator.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author : choi-ys
 * @date : 2022/01/07 4:30 오후
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Service:UserSignup")
class UserSignupServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserSignupService userSignupService;

    @Test
    @DisplayName("회원가입")
    public void signup() {
        // Given
        SignupRequest signupRequest = new SignupRequest(EMAIL, PASSWORD, NAME);
        given(userRepo.existsByEmail(anyString())).willReturn(false);
        given(userRepo.save(any(User.class))).will(AdditionalAnswers.returnsFirstArg());

        // When
        UserResponse expected = userSignupService.signup(signupRequest);

        // Then
        assertAll(
                () -> then(expected.getEmail()).isEqualTo(signupRequest.getEmail()),
                () -> then(expected.getName()).isEqualTo(signupRequest.getName())
        );

        verify(userRepo).existsByEmail(anyString());
        verify(userRepo).save(any(User.class));
        verify(passwordEncoder).encode(anyString());
    }

    @Test
    @DisplayName("회원 가입 실패: 이메일 중복")
    public void signupFail_causeDuplicatedEmail() {
        // Given
        SignupRequest signupRequest = new SignupRequest(EMAIL, PASSWORD, NAME);
        given(userRepo.existsByEmail(anyString())).willReturn(true);

        // When & Then
        thenThrownBy(() ->
                userSignupService.signup(signupRequest)
        ).isInstanceOf(RuntimeException.class);
    }
}