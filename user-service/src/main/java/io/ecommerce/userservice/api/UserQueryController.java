package io.ecommerce.userservice.api;

import io.ecommerce.userservice.common.utils.PageUtils;
import io.ecommerce.userservice.core.domain.dto.request.UserSearchRequest;
import io.ecommerce.userservice.core.service.UserQueryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author : choi-ys
 * @date : 2022/01/08 2:35 오후
 */
@RestController
@RequestMapping("user")
public class UserQueryController {

    private final UserQueryService userQueryService;

    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userQueryService.findById(id));
    }

    @GetMapping
    public ResponseEntity search(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDateTime createdAt,
            @RequestParam(required = false) LocalDateTime updatedAt,
            @PageableDefault(
                    page = 0,
                    size = 30,
                    sort = "id",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {
        return ResponseEntity.ok(userQueryService.userSearch(
                UserSearchRequest.of(
                        email,
                        name,
                        createdAt,
                        updatedAt,
                        PageUtils.pageNumberToIndex(pageable)
                )
        ));
    }
}
