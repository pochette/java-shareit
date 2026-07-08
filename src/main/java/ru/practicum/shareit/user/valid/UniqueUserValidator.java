package ru.practicum.shareit.user.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Objects;

@RequiredArgsConstructor

public class UniqueUserValidator implements ConstraintValidator<UniqueUser, User> {
    private final UserRepository userRepository;

    @Override
    public void initialize(UniqueUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        return userRepository.getAllUsers().stream()
                .noneMatch(user ->
                        Objects.equals(user.getEmail(), value.getEmail()));
    }
}
