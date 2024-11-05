package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable final Long userId) {
        return userService.getById(userId);
    }

    @PostMapping
    public User createUser(@RequestBody UserCreateDto userCreateDto) {
        return userService.create(userCreateDto);
    }

    @PatchMapping("/{userId}")
    public User updateUser(@PathVariable final Long userId,
                           @RequestBody UserUpdateDto userUpdateDto) {
        userUpdateDto.setId(userId);
        return userService.update(userUpdateDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable final Long userId) {
        userService.delete(userId);
    }
}
