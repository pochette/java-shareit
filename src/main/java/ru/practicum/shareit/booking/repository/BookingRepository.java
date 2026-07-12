package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
            "AND b.status = REJECTED " +
            "ORDER BY b.start DESC ")
    List<Booking> findRejectedBookings(Long userId);


    @Query("SELECT b " +
            "FROM Booking b " +
            "WHERE b.user.id = :userId " +
            "and b.status = WAITING " +
            "ORDER BY b.start DESC")
    List<Booking> findWaitingBookings(Long userId);

}

