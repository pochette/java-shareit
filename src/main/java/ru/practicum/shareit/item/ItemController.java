package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto addNewItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @RequestBody Item item) {
        return itemService.addNewItem(userId, item);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-Id") Long userId,
                               @PathVariable Long itemId) {
        return itemService.getItemById(userId, itemId);
    }

    @GetMapping("/search{text}")
    public List<ItemDto> getItemsBySearchRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @PathVariable(name = "text") String searchText) {
        return itemService.getItemsBySearchRequest(userId, searchText);
    }

    @GetMapping
    public List<ItemDto> getItemsOfOwner(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItemsOfOwner(userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @RequestBody Item item,
                              @PathVariable Long itemId) {
        return itemService.updateItem(userId, item, itemId);
    }
}
