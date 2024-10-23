package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestWithResponsesDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequest create(ItemRequestCreateDto itemRequestCreateDto);

    List<ItemRequestWithResponsesDto> getByUserId(Integer userId);

    ItemRequest getById(Integer itemRequestId);

    ItemRequestWithResponsesDto getByIdWithResponses(Integer itemRequestId);

    List<ItemRequestWithResponsesDto> getAll(Integer userId);
}
