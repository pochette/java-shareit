package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collections;
import java.util.Optional;

public class UserDtoMapper {
    public static UserDto doMap(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .itemDtoList(Optional.ofNullable(user.getItems())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(ItemDtoMapper::doMap)
                        .toList())
                .build();
    }
}
