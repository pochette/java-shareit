package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingFilterState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BookingDto addNewBookingRequest(Long userId, BookingRequestDto booking) {
        Item item = getItemOrThrow(booking);
        User booker = getUserOrThrow(userId);
        checkItemAvailable(item);
        checkBookerIsNotOwner(userId, item);
        checkDate(booking);
        Booking savedBooking = bookingRepository.save(BookingMapper.toEntity(booking, item, booker));
        return BookingMapper.doDto(savedBooking);
    }

    @Override
    public BookingDto approvedBookingByOwner(Long userId, Long bookingId, Boolean isApproved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(
                "Бронирование не существует c id: " + bookingId));
        User owner = userRepository.findById(userId).orElseThrow(() ->
                new ForbiddenOperationException("Пользователь с id " + userId + " не может совершать данную операцию"));
        if (!booking.getItem().getUser().equals(owner)) {
            throw new ForbiddenOperationException(
                    String.format("Пользователь %d не является собственником вещи %d. ",
                            userId, booking.getItem().getId()));
        }

        booking.setStatus(BookingStatus.APPROVED);
        return BookingMapper.doDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto getBooking(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new BookingNotFoundException("Бронирование не существует c id: " + bookingId));
        Item item = booking.getItem();

        if (Objects.equals(userId, booking.getUser().getId()) || Objects.equals(userId, item.getUser().getId())) {
            return BookingMapper.doDto(booking);
        } else {
            throw new ForbiddenOperationException(
                    String.format("Пользователь %d не является собственником вещи %d или субъектом бронирования. ",
                            userId, booking.getItem().getId()));
        }
    }

    @Override
    public List<BookingDto> getListOfBookingByUser(Long userId, BookingFilterState state) {
        List<Booking> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        switch (state) {
            case ALL -> result = bookingRepository.findBookingsByUser_IdOrderByStartDesc(userId);
            case CURRENT -> result = bookingRepository.findCurrentBookings(userId, now);
            case PAST -> result = bookingRepository.findPastBookings(userId, now);
            case FUTURE -> result = bookingRepository.findFutureBookings(userId, now);
            case REJECTED -> result = bookingRepository.findRejectedBookings(userId);
            case WAITING -> result = bookingRepository.findWaitingBookings(userId);
            default -> throw new UnknownStateException("Неверная поисковый запрос статуса бронирования: " + state);
        }
        return result.stream()
                .map(BookingMapper::doDto)
                .toList();
    }

    @Override
    public List<BookingDto> getAllOfBookingByItems(Long userId, BookingFilterState state) {
        return List.of();
    }

    private Item getItemOrThrow(BookingRequestDto booking) {
        return itemRepository.findById(booking.itemId()).orElseThrow(() -> new ItemNotFoundException(
                "Вещи с таким id не существует " + booking.itemId()));
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Пользователь с таким Id не существует: " + userId));
    }

    private static void checkItemAvailable(Item item) {
        if (!item.getAvailable()) {
            throw new ItemNotAvailableException("Вещь не доступна: " + item);
        }
    }

    private static void checkBookerIsNotOwner(Long userId, Item item) {
        if (userId.equals(item.getUser().getId())) {
            throw new ForbiddenOperationException("Нельзя забронировать собственную вещь.");
        }
    }

    private static void checkDate(BookingRequestDto booking) {
        if (booking.start().isAfter(booking.end())) {
            throw new ValidationException("Дата начала не может быть позже даты окончания аренды");
        }
    }

}
