package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<ItemDto> itemDtoList;
}
