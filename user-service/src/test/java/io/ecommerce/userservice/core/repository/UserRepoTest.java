package io.ecommerce.userservice.core.repository;

import io.ecommerce.userservice.config.test.DataJpaTestSupporter;
import io.ecommerce.userservice.core.domain.dto.request.UserSearchRequest;
import io.ecommerce.userservice.core.domain.dto.response.UserResponse;
import io.ecommerce.userservice.core.domain.entity.User;
import io.ecommerce.userservice.generator.mock.UserGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * @author : choi-ys
 * @date : 2022/01/07 3:45 오후
 */
@DataJpaTestSupporter
@DisplayName("Repo:User")
@Import(UserGenerator.class)
class UserRepoTest {

    private final UserRepo userRepo;
    private final UserGenerator userGenerator;

    UserRepoTest(
            UserRepo userRepo,
            UserGenerator userGenerator
    ) {
        this.userRepo = userRepo;
        this.userGenerator = userGenerator;
    }

    @Test
    @DisplayName("회원 엔티티 저장")
    void save() {
        // Given
        String email = "test@gmail.com";
        String password = "password";
        String name = "choi-ys";

        User user = User.of(email, password, name);

        // When
        User expected = userRepo.save(user);

        // Then
        assertAll(
                () -> then(expected.getId()).isNotNull(),
                () -> then(expected.getEmail()).isEqualTo(email),
                () -> then(expected.getPassword()).isEqualTo(password),
                () -> then(expected.getName()).isEqualTo(name),
                () -> then(expected.getCreatedAt()).isNotNull(),
                () -> then(expected.getUpdatedAt()).isNotNull()
        );
    }

    @Test
    @DisplayName("회원 엔티티 조회")
    void findById() {
        // Given
        User savedUser = userGenerator.savedUser();

        // When
        User expected = userRepo.findById(savedUser.getId()).orElseThrow();

        // Then
        assertAll(
                () -> then(expected.getId()).isNotNull(),
                () -> then(expected.getEmail()).isEqualTo(savedUser.getEmail()),
                () -> then(expected.getPassword()).isEqualTo(savedUser.getPassword()),
                () -> then(expected.getName()).isEqualTo(savedUser.getName()),
                () -> then(expected.getCreatedAt()).isNotNull(),
                () -> then(expected.getUpdatedAt()).isNotNull()
        );
    }

    @ParameterizedTest(name = "조건에 따른 회원 검색")
    @MethodSource("providerUserSearchRequest")
    @DisplayName("회원 검색")
    public void searchUserPageBySearchParams(UserSearchRequest userSearchRequest) {
        // Given
        User user = userGenerator.savedUser();

        // When
        Page<UserResponse> userResponses = userRepo.searchUserPageBySearchParams(userSearchRequest);

        // Then
        assertAll(
                () -> then(userResponses.getTotalElements()).isEqualTo(1),
                () -> then(userResponses.getContent().stream()
                        .filter(it -> it.getEmail().equals(user.getEmail()))
                        .count()
                ).isEqualTo(1)
        );
    }

    static List<Arguments> providerUserSearchRequest() {
        return new ArrayList<>(
                List.of(
                        Arguments.of(UserSearchRequest.of(
                                UserGenerator.EMAIL,
                                UserGenerator.NAME,
                                null,
                                null,
                                PageRequest.of(0, 5)
                        )),
                        Arguments.of(UserSearchRequest.of(
                                null,
                                null,
                                null,
                                null,
                                PageRequest.of(0, 5)
                        ))
                )
        );
    }
}