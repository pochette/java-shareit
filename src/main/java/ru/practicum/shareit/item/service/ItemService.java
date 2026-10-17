package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    CommentDto addNewComment(Long userId, Long itemId, CommentRequestDto commentRequestDto);

    ItemDto addNewItem(Long userId, Item item);

    ItemDto getItemById(Long userId, Long itemId);

    List<ItemDto> getItemsBySearchRequest(Long userId, String searchRequest);

    List<ItemDto> getItemsOfOwner(Long userId);

    ItemDto updateItem(Long userId, Item item, Long id);

}
