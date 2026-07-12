package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingsByUser_IdOrderByStartDesc(Long userId);

    @Query("SELECT b " +
            " FROM Booking  b " +
            "WHERE b.user.id = :userId " +
            "and b.start <= :now " +
            "and b.end > :now " +
            "ORDER BY b.start DESC ")
    List<Booking> findCurrentBookings(Long userId, LocalDateTime now);

    Optional<Booking> findFirstByItem_IdAndStatusAndStartAfterOrderByStartAsc(Long id, BookingStatus status, LocalDateTime now);

    Optional<Booking> findFirstByItem_IdAndStatusAndStartBeforeOrderByStartDesc(Long itemId, BookingStatus status, LocalDateTime startBefore);

    @Query("select b " +
            "FROM Booking b " +
            "where b.user.id = :userId " +
            "AND b.start > :now " +
            "ORDER BY b.start DESC")
    List<Booking> findFutureBookings(Long userId, LocalDateTime now);

    @Query("select b " +
            "FROM Booking b " +
            "WHERE b.user.id = :userId " +
            "AND b.end < :now " +
            "order by b.start desc "
    )
    List<Booking> findPastBookings(Long userId, LocalDateTime now);

    @Query("SELECT b " +
            "FROM Booking b " +
            "where b.user.id = :userId " +
            "AND b.status = :status " +
            "ORDER BY b.start DESC ")
    List<Booking> findRejectedBookings(Long userId, BookingStatus status);

    @Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.user.id = :userId " +
            "and b.status = :status " +
            "ORDER BY b.start DESC")
    List<Booking> findWaitingBookings(Long userId, BookingStatus status);

}

