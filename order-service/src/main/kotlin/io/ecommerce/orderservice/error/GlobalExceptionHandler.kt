package io.ecommerce.orderservice.error

import io.ecommerce.orderservice.error.exception.ResourceNotFoundException
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.servlet.http.HttpServletRequest

/**
 * @author : choi-ys
 * @date : 2022/01/09 9:02 오후
 */
@RestControllerAdvice
@Slf4j
class GlobalExceptionHandler {

    // [400] @Valid를 이용한 유효성 검사 시, @RequestBody의 값이 없는 경우 JSR 380 Annotations이 적용된 필드의 Binding Exception
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableException(
        exception: HttpMessageNotReadableException,
        request: HttpServletRequest,
    ): ResponseEntity<*> {
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(
                ErrorCode.HTTP_MESSAGE_NOT_READABLE,
                request)
            )
    }

    // [400] @Valid를 이용한 유효성 검사 시, @RequestBody의 값이 잘못된 경우 JSR 380 Annotations이 적용된 필드의 Binding Exception
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(
        exception: MethodArgumentNotValidException,
        request: HttpServletRequest,
    ): ResponseEntity<*> {
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(
                ErrorCode.METHOD_ARGUMENT_NOT_VALID,
                request,
                exception.bindingResult)
            )
    }

    // [400] @RequestParam, @PathVariable 요청값의 자료형이 잘못된 경우
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun methodArgumentTypeMismatchException(
        exception: MethodArgumentTypeMismatchException,
        request: HttpServletRequest,
    ): ResponseEntity<*> {
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.of(
                ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH,
                request)
            )
    }

    // [403] 리소스 접근에 필요한 권한이 존재 하지 않는 경우, Unauthorized Exception
    @ExceptionHandler(AccessDeniedException::class)
    fun accessDeniedException(
        exception: AccessDeniedException,
        request: HttpServletRequest,
    ): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ErrorResponse.of(
                ErrorCode.ACCESS_DENIED,
                request)
            )
    }

    // [404] 요청에 해당하는 자원이 존재하지 않는 경우
    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundException(
        exception: ResourceNotFoundException,
        request: HttpServletRequest,
    ): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.of(
                ErrorCode.RESOURCE_NOT_FOUND,
                request)
            )
    }

    // [405] 허용하지 않는 Http Method 요청인 경우
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun httpRequestMethodNotSupportedException(
        exception: HttpRequestMethodNotSupportedException,
        request: HttpServletRequest,
    ): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(ErrorResponse.of(
                ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED,
                request)
            )
    }

    // [406] 요청 Accept Type이 잘못된 경우
    @ExceptionHandler(HttpMediaTypeNotAcceptableException::class)
    fun httpMediaTypeNotAcceptableException(
        exception: HttpMediaTypeNotAcceptableException,
        request: HttpServletRequest,
    ): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(ErrorResponse.of(
                ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE,
                request)
            )
    }

    // [415] 요청 Media Type이 잘못된 경우
    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun httpMediaTypeNotSupportedException(
        exception: HttpMediaTypeNotSupportedException,
        request: HttpServletRequest,
    ): ResponseEntity<*> {
        return ResponseEntity
            .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .body(ErrorResponse.of(
                ErrorCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED,
                request)
            )
    }
}