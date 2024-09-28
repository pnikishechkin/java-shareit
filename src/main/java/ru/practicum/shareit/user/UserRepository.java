package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.*;

@Repository
public class UserRepository {
    private static Integer id = 1;
    HashMap<Integer, UserDto> users = new HashMap<>();
    Set<String> emails = new HashSet<>();

    public Optional<UserDto> getByUserId(Integer userId) {
        UserDto user = users.get(userId);
        return Optional.ofNullable(user);
    }

    public UserDto create(UserCreateDto userCreateDto) {
        UserDto userDto = UserMapper.mapToUserDto(userCreateDto);
        userDto.setId(getNewId());
        users.put(userDto.getId(), userDto);
        emails.add(userDto.getEmail());
        return userDto;
    }

    public boolean containsByEmail(String email) {
        return emails.contains(email);
    }

    private Integer getNewId() {
        return id++;
    }
}
