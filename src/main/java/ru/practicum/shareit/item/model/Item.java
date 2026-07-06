package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.item.valid.UniqueItem;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
@Validated
@UniqueItem
public class Item {
    private Long id;
    private Long userId;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private Integer countOfRented;
}