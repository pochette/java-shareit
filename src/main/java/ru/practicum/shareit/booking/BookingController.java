package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.BookingFilterState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto addNewBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @RequestBody BookingRequestDto booking) {

        log.info("POST /bookings, userId={}, booking={}", userId, booking);

        return bookingService.addNewBookingRequest(userId, booking);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approveBooking(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long bookingId,
            @RequestParam(name = "approved") Boolean isApproved) {
         return bookingService.approvedBookingByOwner(userId, bookingId, isApproved);

    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingByBookingId(
            @PathVariable Long bookingId,
            @RequestHeader("X-Sharer-User-Id") Long userId) {

        return bookingService.getBooking(userId, bookingId);
    }

    @GetMapping("/owner")
    public List<BookingDto> getListOfBookingByItems(@RequestParam("X-Sharer-User-Id") Long userId,
                                                    @RequestParam(name = "state", defaultValue = "ALL") BookingFilterState state) {
        return bookingService.getAllOfBookingByItems(userId, state);
    }

    @GetMapping
    public List<BookingDto> getListOfBookingByUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                   @RequestParam(required = false, defaultValue = "ALL") BookingFilterState state) {
        return bookingService.getListOfBookingByUser(userId, state);
    }
}
