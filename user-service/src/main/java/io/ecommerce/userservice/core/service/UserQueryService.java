package io.ecommerce.userservice.core.service;

import io.ecommerce.userservice.core.domain.dto.request.UserSearchRequest;
import io.ecommerce.userservice.core.domain.dto.response.UserResponse;
import io.ecommerce.userservice.core.domain.dto.response.common.PageResponse;
import io.ecommerce.userservice.core.repository.UserRepo;
import io.ecommerce.userservice.error.exception.ResourceNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author : choi-ys
 * @date : 2022/01/08 2:27 오후
 */
@Service
@Transactional(readOnly = true)
public class UserQueryService implements UserDetailsService {

    private UserRepo userRepo;

    public UserQueryService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserResponse findById(Long id) {
        return UserResponse.to(userRepo.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public PageResponse userSearch(UserSearchRequest userSearchRequest) {
        return PageResponse.of(userRepo.searchUserPageBySearchParams(userSearchRequest));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        io.ecommerce.userservice.core.domain.entity.User user = userRepo.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("")
        );
        return new User(user.getEmail(), user.getPassword(), Set.of());
    }
}
