package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.dto.ItemUpdateDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemMapper {

    public static Item mapToItemDto(ItemCreateDto itemCreateDto) {
        Item dto = new Item();
        dto.setName(itemCreateDto.getName());
        dto.setDescription(itemCreateDto.getDescription());
        dto.setAvailable(itemCreateDto.getAvailable());
        dto.setOwner(itemCreateDto.getOwner());
        return dto;
    }

    public static Item mapItemUpdateDtoToItemDto(ItemUpdateDto itemUpdateDto) {
        Item dto = new Item();
        dto.setId(itemUpdateDto.getId());
        dto.setName(itemUpdateDto.getName());
        dto.setDescription(itemUpdateDto.getDescription());
        dto.setAvailable(itemUpdateDto.getAvailable());
        dto.setOwner(itemUpdateDto.getOwner());
        return dto;
    }

}
