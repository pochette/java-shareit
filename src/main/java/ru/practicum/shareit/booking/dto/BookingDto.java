package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.user.dto.UserShortDto;

import java.time.LocalDateTime;

public record BookingDto(Long id,
                         @FutureOrPresent
                         LocalDateTime start,
                         @Future
                         LocalDateTime end,
                         ItemShortDto item,
                         UserShortDto booker,
                         BookingStatus status) {

}
