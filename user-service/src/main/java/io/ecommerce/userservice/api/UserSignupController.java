package io.ecommerce.userservice.api;

import io.ecommerce.userservice.core.domain.dto.request.SignupRequest;
import io.ecommerce.userservice.core.service.UserSignupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author : choi-ys
 * @date : 2022/01/07 4:55 오후
 */
@RestController
@RequestMapping("user")
public class UserSignupController {

    private final UserSignupService userSignupService;

    public UserSignupController(UserSignupService userSignupService) {
        this.userSignupService = userSignupService;
    }

    @PostMapping
    public ResponseEntity signup(
            @Valid @RequestBody SignupRequest signupRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userSignupService.signup(signupRequest));
    }
}
