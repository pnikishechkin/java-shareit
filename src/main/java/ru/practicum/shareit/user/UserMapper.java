package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserUpdateDto;

public class UserMapper {

    public static User mapUserCreateToUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setName(userCreateDto.getName());
        user.setEmail(userCreateDto.getEmail());
        return user;
    }

    public static User mapUserUpdateDtoToUser(UserUpdateDto userUpdateDto) {
        User user = new User();
        user.setId(userUpdateDto.getId());
        user.setName(userUpdateDto.getName());
        user.setEmail(userUpdateDto.getEmail());
        return user;
    }


}
