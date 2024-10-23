package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestWithResponsesDto;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequest createRequest(@RequestHeader(SHARER_USER_ID) Integer userId,
                                     @RequestBody @Valid ItemRequestCreateDto itemRequestCreateDto) {
        itemRequestCreateDto.setAuthorId(userId);
        return itemRequestService.create(itemRequestCreateDto);
    }

    @GetMapping()
    public List<ItemRequestWithResponsesDto> getItemRequestsByUserId(@RequestHeader(SHARER_USER_ID) Integer userId) {
        return itemRequestService.getByUserId(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestWithResponsesDto getItemRequestsByUserId(@RequestHeader(SHARER_USER_ID) Integer userId,
                                                               @PathVariable final Integer requestId) {
        return itemRequestService.getByIdWithResponses(requestId);
    }

    @GetMapping("/all")
    public List<ItemRequestWithResponsesDto> getAllItemRequests(@RequestHeader(SHARER_USER_ID) Integer userId) {
        return itemRequestService.getAll(userId);
    }
}
