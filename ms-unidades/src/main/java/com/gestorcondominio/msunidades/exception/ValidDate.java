package com.gestorcondominio.msunidades.exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "A data não pode estar em branco. A data deve estar no formato YYYY-MM-DD";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
