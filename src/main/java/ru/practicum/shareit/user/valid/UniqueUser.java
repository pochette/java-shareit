package ru.practicum.shareit.user.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.practicum.shareit.item.valid.UniqueItemValidator;

import java.lang.annotation.*;


@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUserValidator.class)
public @interface UniqueUser {
    Class<?>[] groups() default {};

    String message() default "Данные пользователя должны быть уникальными";

    Class<? extends Payload>[] payload() default {};

}
