package ru.practicum.shareit.request;

import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestWithResponsesDto;

import java.util.List;

public class ItemRequestMapper {
    public static ItemRequest toEntity(ItemRequestCreateDto itemRequestCreateDto) {
        ItemRequest entity = new ItemRequest();
        entity.setDescription(itemRequestCreateDto.getDescription());
        return entity;
    }

    public static ItemRequestWithResponsesDto toDto(ItemRequest itemRequest,
                                                    List<Item> items) {
        ItemRequestWithResponsesDto dto = new ItemRequestWithResponsesDto();
        dto.setId(itemRequest.getId());
        dto.setDescription(itemRequest.getDescription());
        dto.setCreated(itemRequest.getCreated());
        dto.setItems(items.stream().map(ItemMapper::toDto).toList());
        return dto;
    }
}
