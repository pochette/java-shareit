package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Collection<Item> findAllByUser_Id(Long userId);

    @Query("SELECT i " +
            "FROM Item i " +
            "WHERE " +
            "i.available=true and " +
            "(lower(i.description) LIKE LOWER(CONCAT('%', :searchRequest, '%')) " +
            "OR LOWER(i.name) LIKE LOWER(CONCAT('%', :searchRequest, '%')))" +
            " ")
    List<Item> findItemsBySearchRequest(@Param("searchRequest") String searchRequest);

 }
