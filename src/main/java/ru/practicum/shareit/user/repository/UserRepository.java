package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {
    User addNewUser(User user);

    void deleteUser(Long userId);

    boolean existsByEmail(String email);

    List<User> getAllUsers();

    User getUserById(Long userId);

    User updateUser(Long userId, User user);
}
