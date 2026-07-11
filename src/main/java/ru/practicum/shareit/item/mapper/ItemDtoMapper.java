package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;


public class ItemDtoMapper {
    public static ItemDto doMap(Item item) {
        return ItemDto.builder()
                .name(item.getName())
                .status(item.getStatus())
                .countOfRented(item.getCountOfRented())
                .available(item.getAvailable())
                .description(item.getDescription())
                .id(item.getId())
                .userId(item.getUser().getId())
                .build();
    }
}
