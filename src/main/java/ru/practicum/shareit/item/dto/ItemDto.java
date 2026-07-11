package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.model.StatusOfAvailable;

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
    private StatusOfAvailable status;
    private Long userId;
    private boolean available;

}
