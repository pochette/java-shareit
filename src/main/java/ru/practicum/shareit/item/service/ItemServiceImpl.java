package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public ItemDto addNewItem(Long userId, Item item) {

        return ItemDtoMapper.doMap(itemRepository.addNewItem(userId, item));
    }

    @Override
    public ItemDto updateItem(Long userId, Item item, Long id) {
        Item fromDao = itemRepository.getItemById(id);
        Item forUpdate = Item.builder()
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
        if (userId.equals(fromDao.getOwner().getOwnersId())) {
            return ItemDtoMapper.doMap(itemRepository.updateItem(id, forUpdate));

        }
        return null;
    }

    @Override
    public List<ItemDto> getItemsOfOwner(Long userId) {
        return itemRepository.getItemsOfOwner(userId).stream()
                .map(ItemDtoMapper::doMap)
                .toList();
    }

    @Override
    public ItemDto getItemById(Long userId, Long itemId) {
        return ItemDtoMapper.doMap(itemRepository.getItemById(itemId));
    }

    @Override
    public List<ItemDto> getItemsBySearchRequest(Long userId, String searchRequest) {
        return itemRepository.getItemsBySearchRequest(searchRequest).stream().map(ItemDtoMapper::doMap).toList();
    }
}