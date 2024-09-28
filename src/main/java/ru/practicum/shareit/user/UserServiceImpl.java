package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.exception.AlreadyExistException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;

@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserDto getById(Integer userId) {
        return userRepository.getByUserId(userId).orElseThrow(() -> new NotFoundException("Ошибка! Пользователя с " +
                "заданным идентификатором не существует"));
    }

    public UserDto create(UserCreateDto userCreateDto) {
        if (userRepository.containsByEmail(userCreateDto.getEmail())) {
            throw new AlreadyExistException("Ошибка! Пользователь с " +
                    "заданным адресом электронной почты уже существует");
        }
        return userRepository.create(userCreateDto);
    }
}
