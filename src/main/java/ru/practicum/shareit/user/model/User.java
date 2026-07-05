package ru.practicum.shareit.user.model;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class User {
    private Long ownersId;
    private String owner;
    private List<Item> itemList;
}
