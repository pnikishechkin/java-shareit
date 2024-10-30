package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc
class BookingControllerTest {
    @MockBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";

    private Long userId;
    private Long bookingId;
    private BookingState bookingState;
    private String state;
    private BookingCreateDto bookingCreateDto;
    private Booking booking;
    private User user;
    private Item item;
    private LocalDateTime start;
    private LocalDateTime end;
    private Boolean approved;

    @BeforeEach
    void init() {
        userId = 1L;
        bookingId = 1L;
        state = "all";
        bookingState = BookingState.ALL;
        approved = true;

        user = new User();
        user.setId(1L);
        user.setName("Name");
        user.setEmail("email@email.com");

        item = new Item();
        item.setId(1L);
        item.setName("Name");
        item.setDescription("Description");
        item.setOwner(null);
        item.setAvailable(true);

        start = LocalDateTime.of(2025, 5, 2, 12, 10, 10);
        end = LocalDateTime.of(2025, 6, 2, 12, 10, 10);

        bookingCreateDto = new BookingCreateDto();
        bookingCreateDto.setStart(start);
        bookingCreateDto.setEnd(end);
        bookingCreateDto.setBookerId(userId);
        bookingCreateDto.setItemId(1L);
        bookingCreateDto.setStatus("waiting");

        booking = new Booking();
        booking.setId(bookingId);
        booking.setStart(start);
        booking.setEnd(end);
        booking.setBooker(user);
        booking.setItem(item);
        booking.setStatus(BookingStatus.WAITING);
    }

    @Test
    void testGetBookings() throws Exception {

        // Задание поведения мока
        when(bookingService.getBookingsByBooker(userId, BookingState.PAST)).thenReturn(Collections.emptyList());

        // Имитируем вызов запроса и проверяем ответ
        // when + then
        mockMvc.perform(get("/bookings")
                        .header(SHARER_USER_ID, userId)
                        .param("state", state))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        // Проверяем, что был вызван нужный метод сервиса с нужными параметрами, один раз
        verify(bookingService, times(1)).getBookingsByBooker(userId, bookingState);
    }

    @Test
    void testCreateBooking() throws Exception {
        when(bookingService.create(bookingCreateDto)).thenReturn(booking);

        mockMvc.perform(post("/bookings")
                        .header(SHARER_USER_ID, userId)
                        .content(mapper.writeValueAsString(bookingCreateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(booking.getId()), Long.class))
                .andExpect(jsonPath("$.status", is(BookingStatus.WAITING.toString())))
                .andExpect(jsonPath("$.start", is(start.toString())))
                .andExpect(jsonPath("$.end", is(end.toString())));

        verify(bookingService, times(1)).create(bookingCreateDto);
    }

    @Test
    void testGetBooking() throws Exception {
        when(bookingService.getById(userId, bookingId)).thenReturn(booking);

        mockMvc.perform(get("/bookings/{bookingId}", bookingId)
                        .header(SHARER_USER_ID, userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(booking.getId()), Long.class))
                .andExpect(jsonPath("$.status", is(BookingStatus.WAITING.toString())))
                .andExpect(jsonPath("$.start", is(start.toString())))
                .andExpect(jsonPath("$.end", is(end.toString())));

        verify(bookingService, times(1)).getById(userId, bookingId);
    }

    @Test
    void testApproveBooking() throws Exception {
        when(bookingService.approve(userId, bookingId, approved)).thenReturn(booking);

        mockMvc.perform(patch("/bookings/{bookingId}", bookingId)
                        .header(SHARER_USER_ID, userId)
                        .param("approved", approved.toString())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(booking.getId()), Long.class))
                .andExpect(jsonPath("$.status", is(BookingStatus.WAITING.toString())))
                .andExpect(jsonPath("$.start", is(start.toString())))
                .andExpect(jsonPath("$.end", is(end.toString())));

        verify(bookingService, times(1)).approve(userId, bookingId, approved);
    }

    @Test
    void testGetBookingsByUserItems() throws Exception {
        when(bookingService.getBookingsByOwner(userId, bookingState)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/bookings/owner", bookingId)
                        .header(SHARER_USER_ID, userId)
                        .param("approved", approved.toString())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(bookingService, times(1)).getBookingsByOwner(userId, bookingState);
    }
}