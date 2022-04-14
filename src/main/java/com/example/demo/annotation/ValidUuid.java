package com.example.demo.annotation;

import com.example.demo.validator.UuidValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UuidValidator.class)
public @interface ValidUuid {

    String message() default "{invalid staffUUid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}