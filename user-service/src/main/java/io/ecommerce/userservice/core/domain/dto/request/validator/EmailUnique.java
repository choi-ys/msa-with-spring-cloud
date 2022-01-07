package io.ecommerce.userservice.core.domain.dto.request.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author : choi-ys
 * @date : 2022/01/04 11:09 오전
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailDuplicationValidator.class)
public @interface EmailUnique {

    String message() default "Email is Duplication";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
