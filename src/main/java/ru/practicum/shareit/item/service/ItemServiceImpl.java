package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.ForbiddenOperationException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.CommentDtoMapper;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentDto addNewComment(Long userId, Long itemId, CommentRequestDto commentRequestDto) {
        log.info("Added comment '{}' by user: {}, for item: {}", commentRequestDto, userId, itemId);
        Item item = itemRepository
            .findById(itemId)
            .orElseThrow(() ->
                new ItemNotFoundException(String.format("Вещь с id %d не найдена", itemId)));
//        User user = userRepository
//            .findById(userId)
//            .orElseThrow(() ->
//                new UserNotFoundException("Попытка добавить комментарий у несуществующего пользователя."));
        Booking booking = bookingRepository
            .findFinishedBooking(userId, itemId)
            .orElseThrow(() ->
                new ValidationException("Ошибка при валидации бронировании при добавлении комментария."));

        Comment comment = new Comment();
        comment.setUser(booking.getUser());
        comment.setItem(item);
        comment.setCreated(LocalDateTime.now());
        comment.setText(commentRequestDto.text());

        return CommentDtoMapper.doMap(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public ItemDto addNewItem(Long userId, Item item) {

        log.info("Adding item '{}' for booker {}", item.getName(), userId);
        User user = userRepository
            .findById(userId)
            .orElseThrow(() ->
                new UserNotFoundException("Попытка обновить вещь у несуществующего пользователя."));
        item.setUser(user);

        ItemDto dto = ItemDtoMapper.doMap(itemRepository.save(item));

        log.info("Item {} successfully created", dto.getId());

        return dto;
    }

    @Override
    public ItemDto getItemById(Long userId, Long itemId) {
        return ItemDtoMapper.doMap(itemRepository
            .findById(itemId)
            .orElseThrow(() ->
                new ItemNotFoundException(String.format("Вещь с id %d не найдена", itemId))));
    }

    @Override
    public List<ItemDto> getItemsBySearchRequest(Long userId, String searchRequest) {
        log.debug("Searching items by '{}'", searchRequest);
        if (searchRequest == null || searchRequest.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository
            .findItemsBySearchRequest((((searchRequest))))
            .stream()
            .map(ItemDtoMapper::doMap)
            .toList();
    }

    @Override
    public List<ItemDto> getItemsOfOwner(Long userId) {
        return itemRepository
            .findAllByUser_Id(userId)
            .stream()
            .map(item -> {
                ItemDto dto = ItemDtoMapper.doMap(item);

                setNextAndLastBooking(dto);
                return dto;
            })
            .toList();
    }

    @Transactional
    @Override
    public ItemDto updateItem(Long userId, Item item, Long id) {
        Item fromDao = itemRepository
            .findById(id)
            .orElseThrow(() -> new ItemNotFoundException(String.format("Вещь с id %d не найдена", id)));
        boolean isOwner = Objects.equals(userId, fromDao
            .getUser()
            .getId());
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

    private <T> void updateIfPresent(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

    private void setNextAndLastBooking(ItemDto dto) {
        LocalDateTime now = LocalDateTime.now();
        bookingRepository
            .findFirstByItem_IdAndStatusAndStartBeforeOrderByStartDesc(
                dto.getId(),
                BookingStatus.APPROVED,
                now)
            .ifPresent(b -> dto.setLastBooking(b.getStart()));

        bookingRepository
            .findFirstByItem_IdAndStatusAndStartAfterOrderByStartAsc(
                dto.getId(),
                BookingStatus.APPROVED,
                now)
            .ifPresent(b -> dto.setNextBooking(b.getStart()));
    }

}