package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.*;

import java.util.List;

public interface ItemService {
    ItemWithCommentsDto getDtoById(Integer itemId);

    List<ItemWithCommentsDto> getByUserId(Integer userId);

    Item create(ItemCreateDto itemCreateDto);

    List<Item> search(String text);

    Item update(ItemUpdateDto itemUpdateDto);

    CommentShowDto postComment(CommentCreateDto commentCreateDto);

    Item getById(Integer itemId);
}
