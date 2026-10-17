package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserShortDto;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {
    public static BookingDto doDto(Booking booking) {

        return new BookingDto(booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                new ItemShortDto(booking.getItem().getId(), booking.getItem().getName()),
                new UserShortDto(booking.getUser().getId()),
                booking.getStatus());
    }

    public static Booking toEntity(BookingRequestDto dto, Item item, User user) {

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setStart(dto.start());
        booking.setEnd(dto.end());
        booking.setItem(item);
        booking.setStatus(BookingStatus.WAITING);
        return booking;
    }

}
