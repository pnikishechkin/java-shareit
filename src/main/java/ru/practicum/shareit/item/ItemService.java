package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

import java.util.Set;

public interface ItemService {
    Item getById(Integer itemId);

    Set<Item> getByUserId(Integer userId);

    Item create(ItemCreateDto itemCreateDto);

    Set<Item> search(String text);

    Item update(ItemUpdateDto itemUpdateDto);
}
