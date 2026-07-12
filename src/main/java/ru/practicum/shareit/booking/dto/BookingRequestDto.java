package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDateTime;

public record BookingRequestDto(Long itemId,
                                @FutureOrPresent
                                LocalDateTime start,
                                @Future
                                LocalDateTime end) {

}
