package io.ecommerce.productgservice.error

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.validation.BindingResult
import java.time.LocalDateTime
import java.util.ArrayList
import javax.servlet.http.HttpServletRequest

/**
 * @author : choi-ys
 * @date : 2022-01-21 오전 12:41
 */
data class ErrorResponse(

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    val timestamp: LocalDateTime = LocalDateTime.now(),

    var code: String? = null,
    var message: String? = null,
    var method: String? = null,
    var path: String? = null,

    var errorDetails: List<ErrorDetail>? = null,
) {

    constructor(
        errorCode: ErrorCode,
        httpServletRequest: HttpServletRequest,
    ) : this() {
        code = errorCode.code
        message = errorCode.message
        method = httpServletRequest.method
        path = httpServletRequest.requestURI
        errorDetails = ArrayList()
    }


    constructor(
        errorCode: ErrorCode,
        httpServletRequest: HttpServletRequest,
        bindingResult: BindingResult,
    ) : this() {
        this.code = errorCode.code
        message = errorCode.message
        method = httpServletRequest.method
        path = httpServletRequest.requestURI
        errorDetails = ErrorDetail.of(bindingResult)
    }

    companion object {

        fun of(
            errorCode: ErrorCode,
            httpServletRequest: HttpServletRequest,
        ): ErrorResponse {
            return ErrorResponse(errorCode, httpServletRequest)
        }

        fun of(
            errorCode: ErrorCode,
            httpServletRequest: HttpServletRequest,
            bindingResult: BindingResult,
        ): ErrorResponse {
            return ErrorResponse(errorCode, httpServletRequest, bindingResult)
        }
    }

}