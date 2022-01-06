package io.ecommerce.userservice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : choi-ys
 * @date : 2022-01-06 오후 10:19
 */
@RestController
@RequestMapping(value = "index")
public class IndexController {

    @GetMapping
    public String index(
            @RequestHeader(
                    name = "request-header-name",
                    defaultValue = "empty-header"
            ) String header
    ) {
        String message = "user-api-service";
        return String.format("%s:%s", header, message);
    }
}
