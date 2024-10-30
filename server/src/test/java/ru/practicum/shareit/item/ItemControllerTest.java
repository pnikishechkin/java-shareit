package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc
class ItemControllerTest {

    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    private Long itemId;
    private Long userId;
    private Item item;
    private String text;
    private ItemCreateDto itemCreateDto;
    private ItemUpdateDto itemUpdateDto;
    private ItemWithCommentsDto itemWithCommentsDto;
    private CommentCreateDto commentCreateDto;
    private CommentShowDto commentShowDto;

    @BeforeEach
    void init() {

        itemId = 1L;
        userId = 1L;
        text = "Name";

        item = new Item();
        item.setId(itemId);
        item.setName("Name");
        item.setDescription("Description");
        item.setAvailable(true);

        itemCreateDto = new ItemCreateDto();
        itemCreateDto.setName("Name");
        itemCreateDto.setDescription("Description");
        itemCreateDto.setOwnerId(1L);
        itemCreateDto.setRequestId(1L);
        itemCreateDto.setAvailable(true);

        itemUpdateDto = new ItemUpdateDto();
        itemUpdateDto.setId(itemId);
        itemUpdateDto.setName("Name");
        itemUpdateDto.setDescription("Description");
        itemUpdateDto.setOwnerId(1L);
        itemUpdateDto.setAvailable(true);

        itemWithCommentsDto = new ItemWithCommentsDto();
        itemWithCommentsDto.setId(itemId);
        itemWithCommentsDto.setName("Name");
        itemWithCommentsDto.setDescription("Description");
        itemWithCommentsDto.setAvailable(true);

        commentCreateDto = new CommentCreateDto();
        commentCreateDto.setItemId(itemId);
        commentCreateDto.setAuthorId(userId);
        commentCreateDto.setText("text");
        commentCreateDto.setCreated(LocalDateTime.of(2024, 5, 5, 5, 5, 5));

        commentShowDto = new CommentShowDto();
        commentShowDto.setId(1L);
        commentShowDto.setText("text");
        commentShowDto.setCreated(LocalDateTime.of(2024, 5, 5, 5, 5, 5));
    }

    @Test
    void testGetItemsByUserId() throws Exception {
        when(itemService.getByUserId(userId)).thenReturn(List.of(itemWithCommentsDto));

        mockMvc.perform(get("/items")
                        .header(SHARER_USER_ID, userId))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Name\",\"description\":\"Description\",\"owner\":null,\"available\":true,\"comments\":null,\"lastBooking\":null,\"nextBooking\":null}]"));

        verify(itemService, times(1)).getByUserId(userId);
    }

    @Test
    void testGetItemById() throws Exception {
        when(itemService.getDtoById(userId)).thenReturn(itemWithCommentsDto);

        mockMvc.perform(get("/items/{itemId}", itemId))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Name\",\"description\":\"Description\",\"owner\":null,\"available\":true,\"comments\":null,\"lastBooking\":null,\"nextBooking\":null}"));

        verify(itemService, times(1)).getDtoById(userId);
    }

    @Test
    void testSearchItems() throws Exception {
        when(itemService.search(text)).thenReturn(List.of(item));

        mockMvc.perform(get("/items/search")
                        .param("text", text))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Name\",\"description\":\"Description\",\"owner\":null,\"available\":true,\"request\":null}]"));

        verify(itemService, times(1)).search(text);
    }

    @Test
    void testCreateItem() throws Exception {
        when(itemService.create(itemCreateDto)).thenReturn(item);

        mockMvc.perform(post("/items")
                        .header(SHARER_USER_ID, userId)
                        .content(mapper.writeValueAsString(itemCreateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Name\",\"description\":\"Description\",\"owner\":null,\"available\":true,\"request\":null}"));

        verify(itemService, times(1)).create(itemCreateDto);
    }

    @Test
    void testUpdateItem() throws Exception {
        when(itemService.update(itemUpdateDto)).thenReturn(item);

        mockMvc.perform(patch("/items/{itemId}", itemId)
                        .header(SHARER_USER_ID, userId)
                        .content(mapper.writeValueAsString(itemUpdateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Name\",\"description\":\"Description\",\"owner\":null,\"available\":true,\"request\":null}"));

        verify(itemService, times(1)).update(itemUpdateDto);
    }

    @Test
    void testPostComment() throws Exception {
        when(itemService.postComment(commentCreateDto)).thenReturn(commentShowDto);

        mockMvc.perform(post("/items/{itemId}/comment", itemId)
                        .header(SHARER_USER_ID, userId)
                        .content(mapper.writeValueAsString(commentCreateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"text\":\"text\",\"authorName\":null,\"created\":\"2024-05-05T05:05:05\"}"));

        verify(itemService, times(1)).postComment(commentCreateDto);
    }
}