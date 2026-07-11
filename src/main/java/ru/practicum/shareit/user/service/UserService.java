package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {

    UserDto getUserById(Long userId);

    UserDto addNewUser(User user);

    UserDto updateUser(Long userId, User user);

    void deleteUser(Long userId);


}
