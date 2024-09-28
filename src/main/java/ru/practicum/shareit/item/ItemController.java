package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;

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
    public Set<ItemDto> getItemsByUserId(@RequestHeader(SHARER_USER_ID) Integer userId) {
        return itemService.getByUserId(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader(SHARER_USER_ID) Integer userId,
                               @PathVariable @Positive final Integer itemId) {
        return itemService.getById(itemId);
    }

    @GetMapping("/search")
    public Set<ItemDto> searchItems(@RequestParam String text) {
        return itemService.search(text);
    }

    @PostMapping
    public ItemDto createItem(@RequestHeader(SHARER_USER_ID) Integer userId,
            @RequestBody @Valid ItemCreateDto itemDto) {
        itemDto.setOwnerId(userId);
        return itemService.create(itemDto);
    }

    // Patch
}
