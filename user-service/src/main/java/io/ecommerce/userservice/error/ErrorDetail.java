package io.ecommerce.userservice.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : choi-ys
 * @date : 2022/01/07 6:43 오후
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorDetail {
    private String object;
    private String field;
    private String code;
    private String rejectMessage;
    private Object rejectedValue;

    private ErrorDetail(
            final String object,
            final String field,
            final String code,
            final String rejectMessage,
            final Object rejectedValue
    ) {
        this.object = object;
        this.field = field;
        this.code = code;
        this.rejectMessage = rejectMessage;
        this.rejectedValue = rejectedValue;
    }

    public static List<ErrorDetail> of(final BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream()
                .map(it ->
                        new ErrorDetail(
                                it.getObjectName(),
                                it.getField(),
                                it.getCode(),
                                it.getDefaultMessage(),
                                it.getRejectedValue()
                        )
                ).collect(Collectors.toList());
    }
}
