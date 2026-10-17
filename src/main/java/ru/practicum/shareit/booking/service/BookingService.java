package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.BookingFilterState;

import java.util.List;

public interface BookingService {
    BookingDto addNewBookingRequest(Long userId, BookingRequestDto booking);

    BookingDto approvedBookingByOwner(Long userId, Long bookingId, Boolean isApproved);

    List<BookingDto> getAllOfBookingByItems(Long userId, BookingFilterState state);

    BookingDto getBooking(Long userId, Long bookingId);

    List<BookingDto> getListOfBookingByUser(Long userId, BookingFilterState state);

}
