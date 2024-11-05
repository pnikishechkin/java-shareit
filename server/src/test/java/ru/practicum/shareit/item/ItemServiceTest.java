package ru.practicum.shareit.item;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.BaseTestData;
import ru.practicum.shareit.exception.AlreadyExistException;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentShowDto;
import ru.practicum.shareit.item.dto.ItemWithCommentsDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
class ItemServiceTest extends BaseTestData {

    private final ItemService service;

    @Test
    void getById_correctId() {
        Item testItem = service.getById(1L);
        assertEquals(item1, testItem);
    }

    @Test
    void getDtoById_correctId() {
        ItemWithCommentsDto testItem = service.getDtoById(1L);
        assertEquals(item1.getId(), testItem.getId());
        assertEquals(item1.getName(), testItem.getName());
    }

    @Test
    void getById_notExist() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getById(11L));
        assertEquals(ex.getMessage(), "Ошибка! Вещи с заданным идентификатором не существует");
    }

    @Test
    void getDtoById_notExist() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getDtoById(11L));
        assertEquals(ex.getMessage(), "Ошибка! Вещи с заданным идентификатором не существует");
    }

    @Test
    void search_correct() {
        List<Item> items = service.search("Item");
        assertEquals(1, items.size());
        assertEquals(item1, items.getFirst());
    }

    @Test
    void getByUserId_correct() {
        List<ItemWithCommentsDto> items = service.getByUserId(1L);
        assertEquals(1, items.size());
        assertEquals(item1.getName(), items.getFirst().getName());
    }

    @Test
    void getByUserId_notExist() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getByUserId(11L));
        assertEquals(ex.getMessage(), "Ошибка! Пользователя с заданным идентификатором не существует");
    }

    @Test
    void create_correct() {
        Item newItem = service.create(itemCreateDto);
        assertEquals(3L, newItem.getId());
        assertDoesNotThrow(() -> service.getById(3L));
        Item testItem = service.getById(3L);
        assertEquals(itemCreateDto.getName(), testItem.getName());
    }

    @Test
    void update_correct() {
        Item updateItem = service.update(itemUpdateDto);
        assertEquals("Item updated", updateItem.getName());
        Item updatedItem = service.getById(1L);
        assertEquals("Item updated", updatedItem.getName());
    }

    @Test
    void postComment_correct() {
        CommentShowDto commentShowDto = service.postComment(commentCreateDto);
        assertEquals("New comment", commentShowDto.getText());

        ItemWithCommentsDto testItem = service.getDtoById(2L);
        assertEquals(1, testItem.getComments().size());
        assertEquals("New comment", testItem.getComments().getFirst().getText());
    }

    @Test
    void postComment_notExistBooking() {
        commentCreateDto.setItemId(1L);
        AlreadyExistException ex = assertThrows(AlreadyExistException.class, () -> service.postComment(commentCreateDto));
        assertEquals(ex.getMessage(), "Ошибка! Заказа от данного автора на данную вещь не существовало!");
    }

    @Test
    void postComment_bookingNotApproved() {
        commentCreateDto.setAuthorId(2L);
        commentCreateDto.setItemId(1L);
        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.postComment(commentCreateDto));
        assertEquals(ex.getMessage(), "Невозможно оставить комментарий! Бронирование не было подтверждено или не закончено!");
    }
}
