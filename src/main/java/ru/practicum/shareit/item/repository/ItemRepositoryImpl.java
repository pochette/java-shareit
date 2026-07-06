package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Repository


public class ItemRepositoryImpl implements ItemRepository {
    private static final Map<Long, List<Item>> items = new HashMap<>();
    private static Long id = 0L;

    @Override
    public Item addNewItem(Long userId, Item item) {
        item.setId(setNextId());
        items.computeIfAbsent(item.getId(), id -> new ArrayList<>()).add(item);
        return item;
    }

    @Override
    public Item updateItem(Long itemId, Item item) {
        List<Item> oldItemsList = items.get(itemId);
        Item oldItem = getItemById(itemId);
        oldItemsList.remove(oldItem);
        oldItemsList.add(item);
        items.replace(itemId, oldItemsList);
        return item;
    }

    @Override
    public List<Item> getItemsOfOwner(Long userId) {
        return items.values().stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Item getItemById(Long id) {
        return items.get(id).stream().filter(i -> i.getId().equals(id)).findFirst().orElseThrow();
    }

    @Override
    public List<Item> getItemsBySearchRequest(String searchRequest) {

        if (searchRequest.isBlank()) {
            return Collections.emptyList();
        }
        String searchToLowerCase = searchRequest.toLowerCase(Locale.ROOT);
        return items.values().stream()
                .flatMap(Collection::stream)
                .filter(Item::getAvailable)
                .filter(item ->
                        item.getName().toLowerCase(Locale.ROOT).contains(searchToLowerCase) ||
                                item.getDescription().toLowerCase(Locale.ROOT).contains(searchToLowerCase))
                .toList();
    }

    @Override
    public List<Item> getAllItems() {
        return items.values().stream().flatMap(Collection::stream).toList();
    }

    private static Long setNextId() {
        return id++;

    }
}
