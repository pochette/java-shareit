package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@Data
@Builder
public class UserDto {
    private Long ownersId;
    private List<ItemDto> itemDtoList;
}
