package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";
    private final ItemService itemService;

    @GetMapping()
    public List<ItemWithCommentsDto> getItemsByUserId(@RequestHeader(SHARER_USER_ID) Long userId) {
        return itemService.getByUserId(userId);
    }

    @GetMapping("/{itemId}")
    public ItemWithCommentsDto getItemById(@PathVariable final Long itemId) {
        return itemService.getDtoById(itemId);
    }

    @GetMapping("/search")
    public List<Item> searchItems(@RequestParam String text) {
        return itemService.search(text);
    }

    @PostMapping
    public Item createItem(@RequestHeader(SHARER_USER_ID) Long userId,
                           @RequestBody ItemCreateDto itemCreateDto) {
        itemCreateDto.setOwnerId(userId);
        return itemService.create(itemCreateDto);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestHeader(SHARER_USER_ID) Long userId,
                           @RequestBody ItemUpdateDto itemUpdateDto,
                           @PathVariable final Long itemId) {
        itemUpdateDto.setId(itemId);
        itemUpdateDto.setOwnerId(userId);
        return itemService.update(itemUpdateDto);
    }

    @PostMapping("/{itemId}/comment")
    public CommentShowDto postComment(@RequestHeader(SHARER_USER_ID) Long userId,
                                      @RequestBody CommentCreateDto commentCreateDto,
                                      @PathVariable final Long itemId) {
        commentCreateDto.setAuthorId(userId);
        commentCreateDto.setItemId(itemId);
        return itemService.postComment(commentCreateDto);
    }

}
