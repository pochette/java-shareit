package ru.practicum.shareit.item.service;

import jakarta.validation.constraints.Max;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto addNewItem(Long userId, Item item);

    ItemDto updateItem(Long userId, Item item, Long id);

    List<ItemDto> getItemsOfOwner(Long userId);

    ItemDto getItemById(Long userId, Long itemId);

    List<ItemDto> getItemsBySearchRequest(Long userId, String searchRequest);


}
