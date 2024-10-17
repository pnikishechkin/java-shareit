package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.exception.AlreadyExistException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.model.User;

@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Ошибка! Пользователя с " +
                "заданным идентификатором не существует"));
    }

    @Override
    public User create(UserCreateDto userCreateDto) {

        userRepository.findByEmail(userCreateDto.getEmail()).ifPresent(u -> {
            throw new AlreadyExistException(
                    "Ошибка! Пользователь с заданным адресом электронной почты уже существует");
        });

        return userRepository.save(UserMapper.toEntity(userCreateDto));
    }

    @Override
    public User update(UserUpdateDto userUpdateDto) {

        User user = userRepository.findById(userUpdateDto.getId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));

        userRepository.findByEmail(userUpdateDto.getEmail()).ifPresent(u -> {
            throw new AlreadyExistException(
                    "Ошибка! Пользователь с заданным адресом электронной почты уже существует");
        });

        if (userUpdateDto.getName() == null) {
            userUpdateDto.setName(user.getName());
        }
        if (userUpdateDto.getEmail() == null) {
            userUpdateDto.setEmail(user.getEmail());
        }

        return userRepository.save(UserMapper.toEntity(userUpdateDto));
    }

    @Override
    public void delete(Integer userId) {

        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));

        userRepository.deleteById(userId);
    }
}
