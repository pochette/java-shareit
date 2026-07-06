package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final Map<Long, User> usersMap = new HashMap<>();
    private static Long userId = 1L;

    @Override
    public User addNewUser(User user) {
        user.setId(getNextId());
        usersMap.put(user.getId(), user);
        return usersMap.get(user.getId());
    }

    private static Long getNextId() {
        return userId++;
    }

    @Override
    public void deleteUser(Long userId) {
        usersMap.remove(userId);

    }

    @Override
    public boolean existsByEmail(String email) {
        return usersMap.values().stream()
                .anyMatch(user -> Objects.equals(user.getEmail(), email));
    }

    @Override
    public List<User> getAllUsers() {
        return usersMap.values().stream().toList();
    }

    @Override
    public User getUserById(Long userId) {
        return usersMap.get(userId);
    }

    @Override
    public User updateUser(Long userId, User user) {
        usersMap.replace(userId, user);
        user.setId(userId);
        return usersMap.get(userId);
    }

}
