package io.ecommerce.userservice.core.service;

import io.ecommerce.userservice.core.domain.dto.response.UserResponse;
import io.ecommerce.userservice.core.repository.UserRepo;
import io.ecommerce.userservice.error.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : choi-ys
 * @date : 2022/01/08 2:27 오후
 */
@Service
@Transactional(readOnly = true)
public class UserQueryService {

    private UserRepo userRepo;

    public UserQueryService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserResponse findById(Long id) {
        return UserResponse.to(userRepo.findById(id).orElseThrow(ResourceNotFoundException::new));
    }
}
