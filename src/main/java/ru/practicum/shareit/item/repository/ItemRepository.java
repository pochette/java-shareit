package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item addNewItem(Long userId, Item item);

    List<Item> getAllItems();

    Item getItemById(Long itemId);

    List<Item> getItemsBySearchRequest(String searchRequest);

    List<Item> getItemsOfOwner(Long userId);

    Item updateItem(Long itemId, Item item);

}
