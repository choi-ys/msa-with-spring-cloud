package io.ecommerce.userservice.error;

import io.ecommerce.userservice.error.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : choi-ys
 * @date : 2022/01/07 5:48 오후
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // [400] @Valid를 이용한 유효성 검사 시, @RequestBody의 값이 없는 경우 JSR 380 Annotations이 적용된 필드의 Binding Exception
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity httpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.of(
                        ErrorCode.HTTP_MESSAGE_NOT_READABLE, request)
                );
    }

    // [400] @Valid를 이용한 유효성 검사 시, @RequestBody의 값이 잘못된 경우 JSR 380 Annotations이 적용된 필드의 Binding Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.of(
                        ErrorCode.METHOD_ARGUMENT_NOT_VALID,
                        request,
                        exception.getBindingResult())
                );
    }

    // [400] @RequestParam, @PathVariable 요청값의 자료형이 잘못된 경우
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.of(
                        ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH,
                        request)
                );
    }

    // [401] 유효한 자격 증명이 아닌 접근인 경우, Unauthorized Exception
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity AuthenticationException(Exception exception, HttpServletRequest request) {
        ErrorResponse errorResponse;
        if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorResponse = ErrorResponse.of(ErrorCode.AUTHENTICATION_CREDENTIALS_NOT_FOUND, request);
        } else if (exception instanceof BadCredentialsException) {
            errorResponse = ErrorResponse.of(ErrorCode.BAD_CREDENTIALS, request);
        } else {
            errorResponse = ErrorResponse.of(ErrorCode.UNAUTHORIZED, request);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    // [403] 리소스 접근에 필요한 권한이 존재 하지 않는 경우, Unauthorized Exception
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity accessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.of(
                        ErrorCode.ACCESS_DENIED,
                        request)
                );
    }

    // [404] 요청에 해당하는 자원이 존재하지 않는 경우
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity resourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(
                        ErrorCode.RESOURCE_NOT_FOUND,
                        request)
                );
    }

    // [405] 허용하지 않는 Http Method 요청인 경우
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ErrorResponse.of(
                        ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED,
                        request)
                );
    }

    // [406] 요청 Accept Type이 잘못된 경우
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity httpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ErrorResponse.of(
                        ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE,
                        request)
                );
    }

    // [415] 요청 Media Type이 잘못된 경우
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(ErrorResponse.of(
                        ErrorCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED,
                        request)
                );
    }
}
