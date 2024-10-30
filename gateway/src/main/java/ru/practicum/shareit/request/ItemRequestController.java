package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader(SHARER_USER_ID) Long userId,
                                                @RequestBody @Valid ItemRequestCreateDto itemRequestCreateDto) {
        return itemRequestClient.create(userId, itemRequestCreateDto);
    }

    @GetMapping()
    public ResponseEntity<Object> getItemRequestsByUserId(@RequestHeader(SHARER_USER_ID) Long userId) {
        return itemRequestClient.getByUserId(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequestsByUserId(@RequestHeader(SHARER_USER_ID) Long userId,
                                                          @PathVariable final Long requestId) {
        return itemRequestClient.getByIdWithResponses(userId, requestId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllItemRequests(@RequestHeader(SHARER_USER_ID) Long userId) {
        return itemRequestClient.getAll(userId);
    }
}
