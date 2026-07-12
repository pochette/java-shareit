package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.StatusOfAvailable;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ItemDto {

    private Long id;

    private Integer countOfRented;

    private String name;

    private String description;

    private StatusOfAvailable status;

    private Long userId;

    private Boolean available;

    private LocalDateTime lastBooking;

    private LocalDateTime nextBooking;

    private List<CommentDto> comments;
}