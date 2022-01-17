package io.ecommerce.userservice.core.service;

import io.ecommerce.userservice.core.client.OrderServiceClient;
import io.ecommerce.userservice.core.domain.dto.request.UserSearchRequest;
import io.ecommerce.userservice.core.domain.dto.response.OrderResponse;
import io.ecommerce.userservice.core.domain.dto.response.UserOrderResponse;
import io.ecommerce.userservice.core.domain.dto.response.UserResponse;
import io.ecommerce.userservice.core.domain.dto.response.common.PageResponse;
import io.ecommerce.userservice.core.repository.UserRepo;
import io.ecommerce.userservice.error.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
@Transactional(readOnly = true)
public class UserQueryService implements UserDetailsService {

    private UserRepo userRepo;
    private OrderServiceClient orderServiceClient;

    public UserQueryService(
            UserRepo userRepo,
            OrderServiceClient orderServiceClient
    ) {
        this.userRepo = userRepo;
        this.orderServiceClient = orderServiceClient;
    }

    public UserResponse findById(Long id) {
        UserOrderResponse userOrderResponse = UserOrderResponse.to(userRepo.findById(id).orElseThrow(ResourceNotFoundException::new));
        userOrderResponse.setOrderList(orderServiceClient.getOrders(id).getEmbedded());
        log.info("[USER-SERVICE][findUserById : {}][UserOrderResponse : {}]", id, userOrderResponse);
        return userOrderResponse;
    }

    public PageResponse userSearch(UserSearchRequest userSearchRequest) {
        return PageResponse.of(userRepo.searchUserPageBySearchParams(userSearchRequest));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        io.ecommerce.userservice.core.domain.entity.User user = userRepo.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("요청에 해당하는 사용자를 찾을 수 없습니다.")
        );
        return new User(user.getEmail(), user.getPassword(), Set.of());
    }
}
