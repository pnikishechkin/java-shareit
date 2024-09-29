package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserUpdateDto;

public interface UserService {
    User getById(Integer userId);

    User create(UserCreateDto userCreateDto);

    User update(UserUpdateDto userUpdateDto);

    Boolean delete(Integer userId);
}
