package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class Item {
    private final Long id;
    private final User owner;
    private String title;
    private String description;
    private Boolean isAvailable;
    private Integer countOfRented;
}