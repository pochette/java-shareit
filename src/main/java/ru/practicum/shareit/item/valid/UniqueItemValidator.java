package ru.practicum.shareit.item.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.Objects;

@RequiredArgsConstructor
public class UniqueItemValidator implements ConstraintValidator<UniqueItem, Item> {
    private final ItemRepository itemRepository;

    @Override
    public void initialize(UniqueItem constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Item value, ConstraintValidatorContext context) {
        return itemRepository.getAllItems().stream()
                .noneMatch(item -> Objects.equals(item.getDescription(), (value.getDescription()))
                        && Objects.equals(item.getName(), (value.getName())));
    }

}
