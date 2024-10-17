package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemWithCommentsDto getById(Integer itemId);

    List<ItemWithCommentsDto> getByUserId(Integer userId);

    Item create(ItemCreateDto itemCreateDto);

    List<Item> search(String text);

    Item update(ItemUpdateDto itemUpdateDto);

    CommentShowDto postComment(CommentCreateDto commentCreateDto);
}
