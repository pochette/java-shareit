package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;

public class UserMapper {
    public UserDto doMap(User user) {
        return UserDto.builder()
                .ownersId(user.getOwnersId())
                .itemDtoList(user.getItemList().stream().map(ItemDtoMapper::doMap).toList())
                .build();
    }
}
