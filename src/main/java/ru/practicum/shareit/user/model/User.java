package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class User {

    private Long ownersId;
    @NotNull
    private String name;
    @Email
    private String email;
    private List<Item> itemList;
}
