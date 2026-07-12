package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.user.dto.UserShortDto;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
public record BookingDto(Long id,
                         LocalDateTime start,
                         LocalDateTime end,
                         ItemShortDto item,
                         UserShortDto booker,
                         BookingStatus status) {
}
