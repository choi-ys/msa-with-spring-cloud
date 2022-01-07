package io.ecommerce.userservice.core.service;

import io.ecommerce.userservice.core.domain.dto.request.SignupRequest;
import io.ecommerce.userservice.core.domain.dto.response.UserResponse;
import io.ecommerce.userservice.core.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : choi-ys
 * @date : 2022/01/07 4:20 오후
 */
@Service
@Transactional
public class UserSignupService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserSignupService(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse signup(SignupRequest signupRequest) {
        if (userRepo.existsByEmail(signupRequest.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }

        return UserResponse.to(userRepo.save(signupRequest.toEntity(passwordEncoder)));
    }
}