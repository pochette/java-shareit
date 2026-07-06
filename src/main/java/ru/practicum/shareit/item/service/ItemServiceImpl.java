package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ForbiddenOperationException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto addNewItem(Long userId, Item item) {

        log.info("Adding item '{}' for user {}", item.getName(), userId);
        if (userRepository.getUserById(userId) != null) {
            item.setUserId(userId);
        } else {
            throw new UserNotFoundException("Попытка обновить вещь у несуществующего пользователя.");
        }

        ItemDto dto = ItemDtoMapper.doMap(itemRepository.addNewItem(userId, item));

        log.info("Item {} successfully created", dto.getId());

        return dto;
    }

    @Override
    public ItemDto updateItem(Long userId, Item item, Long id) {
        Item fromDao = itemRepository.getItemById(id);
        boolean isOwner = Objects.equals(userId, fromDao.getUserId());
        if (isOwner) {
            updateIfPresent(item.getAvailable(), fromDao::setAvailable);
            updateIfPresent(item.getName(), fromDao::setName);
            updateIfPresent(item.getDescription(), fromDao::setDescription);
            log.info("Updating item {}, user={}", id, userId);
            return ItemDtoMapper.doMap(itemRepository.updateItem(id, fromDao));
        } else {
            throw new ForbiddenOperationException("Попытка обновления вещи чужим пользователем ");
        }

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
        log.debug("Searching items by '{}'", searchRequest);
        return itemRepository.getItemsBySearchRequest(searchRequest).stream().map(ItemDtoMapper::doMap).toList();
    }

    private <T> void updateIfPresent(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}