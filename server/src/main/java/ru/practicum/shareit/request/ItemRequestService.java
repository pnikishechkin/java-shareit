package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestWithResponsesDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequest create(ItemRequestCreateDto itemRequestCreateDto);

    List<ItemRequestWithResponsesDto> getByUserId(Long userId);

    ItemRequest getById(Long itemRequestId);

    ItemRequestWithResponsesDto getByIdWithResponses(Long itemRequestId);

    List<ItemRequestWithResponsesDto> getAll();
}
