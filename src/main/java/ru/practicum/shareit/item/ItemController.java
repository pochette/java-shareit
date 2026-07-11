package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto addNewItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @Valid @RequestBody Item item) {
        log.info("POST /items, userId={}, item={}", userId, item);
        return itemService.addNewItem(userId, item);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-Id") Long userId,
                               @PathVariable Long itemId) {
        return itemService.getItemById(userId, itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsBySearchRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @RequestParam(name = "text") String text) {
        return itemService.getItemsBySearchRequest(userId, text);
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
