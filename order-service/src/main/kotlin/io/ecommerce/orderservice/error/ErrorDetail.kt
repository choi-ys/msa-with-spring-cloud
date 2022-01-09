package io.ecommerce.orderservice.error

import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import java.util.stream.Collectors

/**
 * @author : choi-ys
 * @date : 2022/01/09 9:02 오후
 */
data class ErrorDetail private constructor(
    val `object`: String,
    val field: String,
    val code: String,
    val rejectMessage: String,
    val rejectedValue: Any,
) {
    companion object {
        fun of(bindingResult: BindingResult): List<ErrorDetail> {
            val fieldErrors = bindingResult.fieldErrors
            return fieldErrors.stream()
                .map { it: FieldError ->
                    ErrorDetail(
                        it.objectName,
                        it.field,
                        it.code,
                        it.defaultMessage,
                        it.rejectedValue
                    )
                }.collect(Collectors.toList())
        }
    }
}