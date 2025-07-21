package ru.yandex.practicum.filmorate.anotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AfterDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterDate {
    String message() default "Date must be after {value}";

    String value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
