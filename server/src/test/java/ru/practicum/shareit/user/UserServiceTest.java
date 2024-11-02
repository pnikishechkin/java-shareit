package ru.practicum.shareit.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.BaseTestData;
import ru.practicum.shareit.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
class UserServiceTest extends BaseTestData {

    private final UserService service;

    @Test
    void getById_correctId() {
        User testUser = service.getById(1L);
        assertEquals(user1, testUser);
    }

    @Test
    void getById_notExist() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getById(11L));
        assertEquals(ex.getMessage(), "Ошибка! Пользователя с заданным идентификатором не существует");
    }

    @Test
    void create_correct() {
        User newUser = service.create(userCreateDto);
        assertEquals(4L, newUser.getId());
        assertDoesNotThrow(() -> service.getById(4L));
        User testUser = service.getById(4L);
        assertEquals(userCreateDto.getName(), testUser.getName());
    }

    @Test
    void update_correct() {
        User updateUser = service.update(userUpdateDto);
        assertEquals("User updated", updateUser.getName());
        User updatedUser = service.getById(1L);
        assertEquals("User updated", updatedUser.getName());
    }

    @Test
    void delete_correct() {
        assertDoesNotThrow(() -> service.getById(3L));
        service.delete(3L);
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getById(3L));
    }
}

