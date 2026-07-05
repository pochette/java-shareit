package ru.practicum.shareit.item.service;

import org.springframework.web.servlet.function.ServerRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto addNewItem(Long userId, Item item);

    ItemDto updateItem(Long userId, Item item);

    List<ItemDto> getItemsOfOwner(Long userId);

    ItemDto getItemById(Long userId);

    List<ItemDto> getItemsBySearchRequest(Long userId, String searchRequest);

}
