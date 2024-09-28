package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Set;

public interface ItemService {
    ItemDto getById(Integer itemId);
    Set<ItemDto> getByUserId(Integer userId);
    ItemDto create(ItemCreateDto itemCreateDto);
    Set<ItemDto> search(String text);
}
