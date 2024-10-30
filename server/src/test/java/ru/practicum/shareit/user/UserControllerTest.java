package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    private Long userId;
    private User user;
    private UserCreateDto userCreateDto;
    private UserUpdateDto userUpdateDto;

    @BeforeEach
    void init() {
        userId = 1L;

        user = new User();
        user.setId(userId);
        user.setName("name");
        user.setEmail("email@email.ru");

        userCreateDto = new UserCreateDto();
        userCreateDto.setName("name");
        userCreateDto.setEmail("email@email.ru");

        userUpdateDto = new UserUpdateDto();
        userUpdateDto.setId(userId);
        userUpdateDto.setName("name");
        userUpdateDto.setEmail("email@email.ru");
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getById(userId)).thenReturn(user);

        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"email\":\"email@email.ru\",\"name\":\"name\"}"));

        verify(userService, times(1)).getById(userId);
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.create(userCreateDto)).thenReturn(user);

        mockMvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userCreateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"email\":\"email@email.ru\",\"name\":\"name\"}"));

        verify(userService, times(1)).create(userCreateDto);
    }

    @Test
    void testUpdateUser() throws Exception {
        when(userService.update(userUpdateDto)).thenReturn(user);

        mockMvc.perform(patch("/users/{userId}", userId)
                        .content(mapper.writeValueAsString(userCreateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"email\":\"email@email.ru\",\"name\":\"name\"}"));

        verify(userService, times(1)).update(userUpdateDto);
    }

    @Test
    void testDeleteUser() throws Exception {

        mockMvc.perform(delete("/users/{userId}", userId))
                .andExpect(status().isOk());

        verify(userService, times(1)).delete(userId);
    }
}