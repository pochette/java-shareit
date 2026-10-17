package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class ItemDtoMapper {

    public static ItemDto doMap(Item item) {

        ItemDto dto = new ItemDto();

        dto.setId(item.getId());
        dto.setCountOfRented(item.getCountOfRented());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setStatus(item.getStatus());
        dto.setAvailable(item.getAvailable());
        dto.setUserId(item
            .getUser()
            .getId());
        dto.setComments(
            item
                .getComments()
                .stream()
                .map(CommentDtoMapper::doMap)
                .toList()
        );

        return dto;
    }
}