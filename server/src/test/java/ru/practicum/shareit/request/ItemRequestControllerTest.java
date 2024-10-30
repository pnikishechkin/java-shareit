package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestWithResponsesDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemRequestController.class)
@AutoConfigureMockMvc
class ItemRequestControllerTest {

    @MockBean
    private ItemRequestService itemRequestService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    private Long userId;
    private Long requestId;
    private ItemRequest itemRequest;
    private ItemRequestCreateDto itemRequestCreateDto;
    private ItemRequestWithResponsesDto itemRequestWithResponsesDto;

    @BeforeEach
    void init() {
        userId = 1L;
        requestId = 1L;

        itemRequest = new ItemRequest();
        itemRequest.setId(requestId);
        itemRequest.setDescription("description");
        itemRequest.setCreated(LocalDateTime.of(2024, 5, 5, 5, 5, 5));

        itemRequestCreateDto = new ItemRequestCreateDto();
        itemRequestCreateDto.setAuthorId(userId);
        itemRequestCreateDto.setDescription("description");

        itemRequestWithResponsesDto = new ItemRequestWithResponsesDto();
        itemRequestWithResponsesDto.setId(requestId);
        itemRequestWithResponsesDto.setCreated(LocalDateTime.of(2024, 5, 5, 5, 5, 5));
    }

    @Test
    void testCreateRequest() throws Exception {
        when(itemRequestService.create(itemRequestCreateDto)).thenReturn(itemRequest);

        mockMvc.perform(post("/requests")
                        .header(SHARER_USER_ID, userId)
                        .content(mapper.writeValueAsString(itemRequestCreateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"author\":null,\"description\":\"description\",\"created\":\"2024-05-05T05:05:05\"}"));

        verify(itemRequestService, times(1)).create(itemRequestCreateDto);
    }

    @Test
    void testGetItemRequestsByUserId() throws Exception {
        when(itemRequestService.getByUserId(userId)).thenReturn(List.of(itemRequestWithResponsesDto));

        mockMvc.perform(get("/requests")
                        .header(SHARER_USER_ID, userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"created\":\"2024-05-05T05:05:05\",\"description\":null,\"items\":null}]"));

        verify(itemRequestService, times(1)).getByUserId(userId);
    }

    @Test
    void testGetItemRequestById() throws Exception {
        when(itemRequestService.getByIdWithResponses(requestId)).thenReturn(itemRequestWithResponsesDto);

        mockMvc.perform(get("/requests/{requestId}", requestId)
                        .header(SHARER_USER_ID, userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"created\":\"2024-05-05T05:05:05\",\"description\":null,\"items\":null}"));

        verify(itemRequestService, times(1)).getByIdWithResponses(requestId);
    }

    @Test
    void testGetAllItemRequests() throws Exception {
        when(itemRequestService.getAll(userId)).thenReturn(List.of(itemRequestWithResponsesDto));

        mockMvc.perform(get("/requests/all")
                        .header(SHARER_USER_ID, userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"created\":\"2024-05-05T05:05:05\",\"description\":null,\"items\":null}]"));

        verify(itemRequestService, times(1)).getAll(userId);
    }

}