package io.ecommerce.gateway.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author : choi-ys
 * @date : 2022/01/11 1:54 오후
 */
@Getter
public class UnAuthorizedException extends RuntimeException {
    private HttpStatus httpStatus;

    public UnAuthorizedException(String message) {
        super(message);
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }
}
