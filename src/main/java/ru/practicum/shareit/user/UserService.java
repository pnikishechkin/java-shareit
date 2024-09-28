package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    UserDto getById(Integer userId);
    UserDto create(UserCreateDto userCreateDto);
}
