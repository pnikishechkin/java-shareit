package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

import java.util.Set;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {

    private final static String SHARER_USER_ID = "X-Sharer-User-Id";
    private final ItemService itemService;

    @GetMapping()
    public Set<Item> getItemsByUserId(@RequestHeader(SHARER_USER_ID) Integer userId) {
        return itemService.getByUserId(userId);
    }

    @GetMapping("/{itemId}")
    public Item getItemById(@PathVariable @Positive final Integer itemId) {
        return itemService.getById(itemId);
    }

    @GetMapping("/search")
    public Set<Item> searchItems(@RequestParam String text) {
        return itemService.search(text);
    }

    @PostMapping
    public Item createItem(@RequestHeader(SHARER_USER_ID) Integer userId,
                           @RequestBody @Valid ItemCreateDto itemCreateDto) {
        itemCreateDto.setOwnerId(userId);
        return itemService.create(itemCreateDto);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestHeader(SHARER_USER_ID) Integer userId,
                           @RequestBody ItemUpdateDto itemUpdateDto,
                           @PathVariable @Positive final Integer itemId) {
        itemUpdateDto.setId(itemId);
        itemUpdateDto.setOwnerId(userId);
        return itemService.update(itemUpdateDto);
    }

}
