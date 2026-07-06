package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item addNewItem(Long userId, Item item);

    Item updateItem (Long itemId, Item item);

    List<Item> getItemsOfOwner(Long userId);

    Item getItemById(Long itemId);

    List<Item> getItemsBySearchRequest(String searchRequest);

}
