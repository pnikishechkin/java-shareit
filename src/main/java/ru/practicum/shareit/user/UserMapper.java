package ru.practicum.shareit.user;

import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;

public class UserMapper {

    public static UserDto mapToUserDto(UserCreateDto userCreateDto) {
        UserDto userDto = new UserDto();
        userDto.setName(userCreateDto.getName());
        userDto.setEmail(userCreateDto.getEmail());
        return userDto;
    }

}
