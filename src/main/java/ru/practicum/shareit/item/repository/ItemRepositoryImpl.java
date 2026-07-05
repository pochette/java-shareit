package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, List<Item>> items = new HashMap<>();

    @Override
    public Item addNewItem(Long userId, Item item) {
        items.computeIfAbsent(item.getId(), id -> new ArrayList<>()).add(item);
        return item;
    }

    @Override
    public Item updateItem(Long userId, Item item) {
        List<Item> oldItemsList = items.get(item.getId());
        Item oldItem = getItemById(item.getId());
        oldItemsList.remove(oldItem);
        oldItemsList.add(item);
        items.replace(item.getId(), oldItemsList);
        return item;
    }

    @Override
    public List<Item> getItemsOfOwner(Long userId) {
        return items.values().stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getOwner().getOwnersId().equals(userId))
                .toList();
    }

    @Override
    public Item getItemById(Long id) {
        return items.get(id).stream().filter(i -> i.getId().equals(id)).findFirst().orElseThrow();
    }

    @Override
    public List<Item> getItemsBySearchRequest(String searchRequest) {

        String searchToLowerCase = searchRequest.toLowerCase(Locale.ROOT);
        return items.values().stream()
                .flatMap(Collection::stream)
                .filter(Item::getIsAvailable)
                .filter(item ->
                        item.getTitle().toLowerCase(Locale.ROOT).contains(searchToLowerCase) ||
                                item.getDescription().toLowerCase(Locale.ROOT).contains(searchToLowerCase))
                .toList();
    }
}
