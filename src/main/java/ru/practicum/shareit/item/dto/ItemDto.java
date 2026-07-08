package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class ItemDto {
    private Long id;
    private Integer countOfRented;
    private String name;
    private String description;
    private boolean available;
}
