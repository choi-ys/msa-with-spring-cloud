package io.ecommerce.userservice.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : choi-ys
 * @date : 2022/01/07 5:46 오후
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime timestamp;

    private String code;
    private String message;
    private String method;
    private String path;

    private List<ErrorDetail> errorDetails;

    private ErrorResponse(
            final ErrorCode errorCode,
            final HttpServletRequest httpServletRequest
    ) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.method = httpServletRequest.getMethod();
        this.path = httpServletRequest.getRequestURI();
        this.errorDetails = new ArrayList<>();
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse of(
            final ErrorCode errorCode,
            final HttpServletRequest httpServletRequest
    ) {
        return new ErrorResponse(errorCode, httpServletRequest);
    }

    private ErrorResponse(
            final ErrorCode errorCode,
            final HttpServletRequest httpServletRequest,
            final BindingResult bindingResult
    ) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.method = httpServletRequest.getMethod();
        this.path = httpServletRequest.getRequestURI();
        this.timestamp = LocalDateTime.now();
        this.errorDetails = ErrorDetail.of(bindingResult);
    }

    public static ErrorResponse of(
            final ErrorCode errorCode,
            final HttpServletRequest httpServletRequest,
            final BindingResult bindingResult
    ) {
        return new ErrorResponse(errorCode, httpServletRequest, bindingResult);
    }
}
