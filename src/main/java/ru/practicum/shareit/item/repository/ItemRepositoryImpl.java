//package ru.practicum.shareit.item.repository;
//
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//import ru.practicum.shareit.booking.model.QBooking;
//import ru.practicum.shareit.item.dto.ItemDto;
//import ru.practicum.shareit.item.model.QItem;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Repository
//public class ItemRepositoryImpl implements ItemRepositoryCustom {
//
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public List<ItemDto> findItemsWithBookingInfo(Long userId) {
//        QItem item = QItem.item;
//        QBooking booking = QBooking.booking;
//
//        LocalDateTime now = LocalDateTime.now();
//
//        return queryFactory
//            .select(Projections.constructor(
//                ItemDto.class,
//                item.id,
//                item.countOfRented,
//                item.description,
//                item.status,
//                item.user.id,
//                item.available,
//                booking.start.max(),
//                booking.start.min()
//            ))
//            .from(item)
//            .leftJoin(booking)
//            .on(booking.item.id.eq(item.id))
//            .where(item.user.id.eq(userId))
//            .groupBy(item.id)
//            .fetch();
//    }
//}
