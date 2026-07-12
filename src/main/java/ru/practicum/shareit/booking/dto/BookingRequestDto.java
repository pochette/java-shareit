package ru.practicum.shareit.booking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingRequestDto (Long itemId, LocalDateTime start, LocalDateTime end){

}
