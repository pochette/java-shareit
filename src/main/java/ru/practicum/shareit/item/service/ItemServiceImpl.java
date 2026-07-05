package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public ItemDto addNewItem(Long userId, Item item) {
        return null;
    }

    @Override
    public ItemDto updateItem(Long userId, Item item) {
        return null;
    }

    @Override
    public List<ItemDto> getItemsOfOwner(Long userId) {
        return List.of();
    }

    @Override
    public ItemDto getItemById(Long userId) {
        return null;
    }

    @Override
    public List<ItemDto> getItemsBySearchRequest(Long userId, String searchRequest) {
        return List.of();
    }
}
