package ru.practicum.shareit.request;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.BaseTestData;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestWithResponsesDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
class ItemRequestServiceTest extends BaseTestData {

    private final ItemRequestService service;

    @Test
    void getById_correctId() {
        ItemRequest testRequest = service.getById(1L);
        assertEquals(request1, testRequest);
    }

    @Test
    void getById_notExist() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getById(11L));
        assertEquals(ex.getMessage(), "Ошибка! Запроса с заданным идентификатором не существует");
    }

    @Test
    void getByIdWithResponses_correctId() {
        ItemRequestWithResponsesDto testRequest = service.getByIdWithResponses(1L);
        assertEquals(request1.getId(), testRequest.getId());
        assertEquals(request1.getDescription(), testRequest.getDescription());
    }

    @Test
    void getByIdWithResponses_notExist() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getByIdWithResponses(11L));
        assertEquals(ex.getMessage(), "Ошибка! Запроса с заданным идентификатором не существует");
    }

    @Test
    void create_correct() {
        ItemRequest newItemRequest = service.create(itemRequestCreateDto);
        assertEquals(3L, newItemRequest.getId());
        assertDoesNotThrow(() -> service.getById(3L));
        ItemRequest testItemRequest = service.getById(3L);
        assertEquals(itemRequestCreateDto.getDescription(), testItemRequest.getDescription());
    }

    @Test
    void getAll_correct() {
        List<ItemRequestWithResponsesDto> listItemRequest = service.getAll();
        assertEquals(2, listItemRequest.size());
        assertEquals("request1", listItemRequest.getFirst().getDescription());
    }
}
