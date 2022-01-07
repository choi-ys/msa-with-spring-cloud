package io.ecommerce.userservice.core.domain.dto.request.validator;

import io.ecommerce.userservice.core.repository.UserRepo;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author : choi-ys
 * @date : 2022/01/04 11:38 오전
 */
@Component
public class EmailDuplicationValidator implements ConstraintValidator<EmailUnique, String> {

    private final UserRepo userRepo;
    private static final String DUPLICATED_MESSAGE = "이미 존재하는 이메일 입니다.";

    public EmailDuplicationValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void initialize(EmailUnique constraintAnnotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        boolean isDuplicated = userRepo.existsByEmail(email);

        if (isDuplicated) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(DUPLICATED_MESSAGE)
                    .addConstraintViolation();
        }

        return !isDuplicated;
    }
}
