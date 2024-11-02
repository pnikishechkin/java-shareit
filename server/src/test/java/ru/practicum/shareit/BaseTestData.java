package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

import java.time.LocalDateTime;

@SpringBootTest
public class BaseTestData {

    protected BookingCreateDto bookingCreateDto;
    protected ItemCreateDto itemCreateDto;
    protected ItemUpdateDto itemUpdateDto;
    protected CommentCreateDto commentCreateDto;
    protected ItemRequestCreateDto itemRequestCreateDto;
    protected UserCreateDto userCreateDto;
    protected UserUpdateDto userUpdateDto;

    protected Booking booking1;
    protected Booking booking2;

    protected User user1;
    protected User user2;
    protected User user3;

    protected Item item1;
    protected Item item2;

    protected LocalDateTime start;
    protected LocalDateTime end;

    protected ItemRequest request1;
    protected ItemRequest request2;

    @BeforeEach
    void init() {
        user1 = new User();
        user1.setId(1L);
        user1.setName("Peter");
        user1.setEmail("peter@gmail.com");

        user2 = new User();
        user2.setId(2L);
        user2.setName("Sergey");
        user2.setEmail("sergey@yandex.ru");

        user3 = new User();
        user3.setId(3L);
        user3.setName("Ivan");
        user3.setEmail("ivan@mail.ru");

        userCreateDto = new UserCreateDto();
        userCreateDto.setName("New user");
        userCreateDto.setEmail("new@email.ru");

        userUpdateDto = new UserUpdateDto();
        userUpdateDto.setId(1L);
        userUpdateDto.setName("User updated");
        userUpdateDto.setEmail("new@email.ru");

        request1 = new ItemRequest();
        request1.setId(1L);
        request1.setDescription("request1");
        request1.setCreated(LocalDateTime.of(2024, 10, 31, 10, 0, 0));
        request1.setAuthor(user1);

        request2 = new ItemRequest();
        request2.setId(2L);
        request2.setDescription("request2");
        request2.setCreated(LocalDateTime.of(2024, 10, 31, 11, 0, 0));
        request2.setAuthor(user2);

        item1 = new Item();
        item1.setId(1L);
        item1.setName("Item 1");
        item1.setDescription("Text");
        item1.setOwner(user1);
        item1.setAvailable(true);

        item2 = new Item();
        item2.setId(2L);
        item2.setName("Item 2");
        item2.setDescription("Text");
        item2.setOwner(user2);
        item2.setAvailable(false);
        item2.setRequest(request2);

        itemCreateDto = new ItemCreateDto();
        itemCreateDto.setName("Item 3");
        itemCreateDto.setDescription("Text");
        itemCreateDto.setOwnerId(1L);
        itemCreateDto.setAvailable(false);
        itemCreateDto.setRequestId(1L);

        itemRequestCreateDto = new ItemRequestCreateDto();
        itemRequestCreateDto.setDescription("description");
        itemRequestCreateDto.setAuthorId(1L);

        commentCreateDto = new CommentCreateDto();
        commentCreateDto.setText("New comment");
        commentCreateDto.setAuthorId(3L);
        commentCreateDto.setItemId(2L);
        commentCreateDto.setCreated(LocalDateTime.of(2027, 7, 2, 12, 10, 10));

        itemUpdateDto = new ItemUpdateDto();
        itemUpdateDto.setId(1L);
        itemUpdateDto.setOwnerId(1L);
        itemUpdateDto.setName("Item updated");

        start = LocalDateTime.of(2025, 5, 2, 12, 10, 10);
        end = LocalDateTime.of(2025, 6, 2, 12, 10, 10);

        bookingCreateDto = new BookingCreateDto();
        bookingCreateDto.setStart(start);
        bookingCreateDto.setEnd(end);
        bookingCreateDto.setBookerId(1L);
        bookingCreateDto.setItemId(1L);
        bookingCreateDto.setStatus("waiting");

        booking1 = new Booking();
        booking1.setId(1L);
        booking1.setStart(start);
        booking1.setEnd(end);
        booking1.setBooker(user2);
        booking1.setItem(item1);
        booking1.setStart(LocalDateTime.of(2024, 10, 31, 10, 10, 0));
        booking1.setEnd(LocalDateTime.of(2025, 11, 11, 12, 0, 0));
        booking1.setStatus(BookingStatus.WAITING);

        booking2 = new Booking();
        booking2.setId(2L);
        booking2.setStart(start);
        booking2.setEnd(end);
        booking2.setBooker(user3);
        booking2.setItem(item2);
        booking2.setStart(LocalDateTime.of(2024, 10, 31, 10, 10, 0));
        booking2.setEnd(LocalDateTime.of(2025, 11, 11, 12, 0, 0));
        booking2.setStatus(BookingStatus.APPROVED);

    }
}
