package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;
    private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    @GetMapping()
    public ResponseEntity<Object> getItemsByUserId(@RequestHeader(SHARER_USER_ID) long userId) {
        log.info("Get item by userId={}", userId);
        return itemClient.getByUserId(userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@PathVariable final Long itemId) {
        return itemClient.getByItemId(itemId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@RequestParam String text) {
        return itemClient.search(text);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader(SHARER_USER_ID) long userId,
                                             @RequestBody @Valid ItemCreateDto itemCreateDto) {
        return itemClient.create(userId, itemCreateDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader(SHARER_USER_ID) long userId,
                                             @RequestBody ItemUpdateDto itemUpdateDto,
                                             @PathVariable final Long itemId) {
        return itemClient.update(userId, itemId, itemUpdateDto);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> postComment(@RequestHeader(SHARER_USER_ID) long userId,
                                              @RequestBody @Valid CommentCreateDto commentCreateDto,
                                              @PathVariable final Long itemId) {
        return itemClient.postComment(userId, itemId, commentCreateDto);
    }
}
