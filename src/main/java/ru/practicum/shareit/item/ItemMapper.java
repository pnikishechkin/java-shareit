package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.model.Item;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemMapper {

    public static ItemDto mapToItemDto(ItemCreateDto itemCreateDto) {
        ItemDto dto = new ItemDto();
        dto.setName(itemCreateDto.getName());
        dto.setDescription(itemCreateDto.getDescription());
        dto.setAvailable(itemCreateDto.getAvailable());
        return dto;
    }
}
