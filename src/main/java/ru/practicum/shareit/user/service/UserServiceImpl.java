package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserDtoMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto getUserById(Long userId) {
        log.debug("Получение пользователя с id={}", userId);

        UserDto user = UserDtoMapper.doMap(userRepository.findById((userId)).orElseThrow(() -> new UserNotFoundException(String.format("Пользователь с id %d не найден", userId
        ))));

        log.debug("Пользователь с id={} успешно найден", userId);
        return user;
    }

    @Transactional
    @Override
    public UserDto addNewUser(User user) {
        if (userRepository.existsUserByEmail((user.getEmail()))) {
            throw new DuplicateEmailException("Пользователь с этим email " + user.getName() + " уже существует");
        }
        log.info("Создание нового пользователя с email={}", user.getEmail());

        UserDto createdUser = UserDtoMapper.doMap(userRepository.save(user));

        log.info("Пользователь успешно создан с id={}", createdUser.getId());
        return createdUser;
    }

    @Transactional
    @Override
    public UserDto updateUser(Long userId, User user) {
        log.info("Обновление пользователя с id={}", userId);

        User oldUser = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String.format("Пользователь с id %d не найден", user.getId()
                )));
        updateIfPresent(user.getName(), oldUser::setName);
        updateIfPresent(user.getEmail(), email -> {
            if (!email.equals(oldUser.getEmail()) &&
                    userRepository.existsUserByEmail(email)) {
                throw new DuplicateEmailException("Пользователь с этим email уже существует. email: " + email);
            }
            oldUser.setEmail(email);
        });

        UserDto updatedUser = UserDtoMapper.doMap(userRepository.save(oldUser));

        log.info("Пользователь с id={} успешно обновлен", userId);
        return updatedUser;
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        log.info("Удаление пользователя с id={}", userId);

        userRepository.deleteUserById(((userId)));

        log.info("Пользователь с id={} успешно удален", userId);
    }

    private <T> void updateIfPresent(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
