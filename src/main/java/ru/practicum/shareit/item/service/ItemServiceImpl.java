package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.ForbiddenOperationException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    @Override
    public ItemDto addNewItem(Long userId, Item item) {

        log.info("Adding item '{}' for booker {}", item.getName(), userId);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Попытка обновить вещь у несуществующего пользователя."));
        item.setUser(user);

        ItemDto dto = ItemDtoMapper.doMap(itemRepository.save(item));

        log.info("Item {} successfully created", dto.getId());

        return dto;
    }

    @Transactional
    @Override
    public ItemDto updateItem(Long userId, Item item, Long id) {
        Item fromDao = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(String.format("Вещь с id %d не найдена", id)));
        boolean isOwner = Objects.equals(userId, fromDao.getUser().getId());
        if (isOwner) {
            updateIfPresent(item.getStatus(), fromDao::setStatus);
            updateIfPresent(item.getName(), fromDao::setName);
            updateIfPresent(item.getDescription(), fromDao::setDescription);
            updateIfPresent(item.getAvailable(), fromDao::setAvailable);
            log.info("Updating item {}, booker={}", id, userId);
            return ItemDtoMapper.doMap(itemRepository.save(fromDao));
        } else {
            throw new ForbiddenOperationException("Попытка обновления вещи чужим пользователем ");
        }

    }

    @Override
    public List<ItemDto> getItemsOfOwner(Long userId) {
        return itemRepository.findAllByUser_Id(userId).stream()
                .map(item -> {
                            ItemDto dto = ItemDtoMapper.doMap(item);

                            setNearestAndLastBooking(dto);
                            return dto;
                        }
                )
                .toList();
    }

    @Override
    public ItemDto getItemById(Long userId, Long itemId) {
        return ItemDtoMapper.doMap(itemRepository.findById(itemId).orElseThrow(() ->
                new ItemNotFoundException(String.format("Вещь с id %d не найдена", itemId))));
    }

    @Override
    public List<ItemDto> getItemsBySearchRequest(Long userId, String searchRequest) {
        log.debug("Searching items by '{}'", searchRequest);
        if (searchRequest == null || searchRequest.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.findItemsBySearchRequest((((searchRequest))))
                .stream()
                .map(ItemDtoMapper::doMap)
                .toList();
    }

    private void setNearestAndLastBooking(ItemDto dto) {
        bookingRepository.findFirstByItem_IdAndStatusAndStartBeforeOrderByStartDesc(
                        dto.getId(),
                        BookingStatus.APPROVED,
                        LocalDateTime.now())
                .ifPresent(b -> dto.setLastBooking(b.getStart()));

        bookingRepository.findFirstByItem_IdAndStatusAndStartAfterOrderByStartAsc(
                        dto.getId(),
                        BookingStatus.APPROVED,
                        LocalDateTime.now())
                .ifPresent(b -> dto.setNearestBooking(b.getStart()));
    }

    private <T> void updateIfPresent(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

}