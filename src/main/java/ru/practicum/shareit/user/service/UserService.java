package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public interface UserService {

    UserDto addNewUser(User user);

    void deleteUser(Long userId);

    UserDto getUserById(Long userId);

    UserDto updateUser(Long userId, User user);

}
