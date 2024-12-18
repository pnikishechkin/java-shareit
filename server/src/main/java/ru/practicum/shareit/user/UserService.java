package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

public interface UserService {
    User getById(Long userId);

    User create(UserCreateDto userCreateDto);

    User update(UserUpdateDto userUpdateDto);

    void delete(Long userId);
}
