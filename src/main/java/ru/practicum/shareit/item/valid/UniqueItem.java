package ru.practicum.shareit.item.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueItemValidator.class)

public @interface UniqueItem {
    Class<?>[] groups() default {};

    String message() default "Данные о вещи быть уникальными";

    Class<? extends Payload>[] payload() default {};

}
