package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.exception.AlreadyExistException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserUpdateDto;

@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getById(Integer userId) {
        return userRepository.getByUserId(userId).orElseThrow(() -> new NotFoundException("Ошибка! Пользователя с " +
                "заданным идентификатором не существует"));
    }

    @Override
    public User create(UserCreateDto userCreateDto) {
        if (userRepository.containsByEmail(userCreateDto.getEmail())) {
            throw new AlreadyExistException("Ошибка! Пользователь с " +
                    "заданным адресом электронной почты уже существует");
        }
        return userRepository.create(userCreateDto);
    }

    @Override
    public User update(UserUpdateDto userUpdateDto) {
        User user = userRepository.getByUserId(userUpdateDto.getId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));

        if (userRepository.containsByEmail(userUpdateDto.getEmail())) {
            throw new AlreadyExistException("Ошибка! Пользователь с " +
                    "заданным адресом электронной почты уже существует");
        }

        if (userUpdateDto.getName() == null) {
            userUpdateDto.setName(user.getName());
        }
        if (userUpdateDto.getEmail() == null) {
            userUpdateDto.setEmail(user.getEmail());
        }

        return userRepository.update(userUpdateDto);
    }

    @Override
    public Boolean delete(Integer userId) {
        userRepository.getByUserId(userId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));
        return userRepository.delete(userId);
    }
}
