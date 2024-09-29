package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserUpdateDto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepository {
    private static Integer id = 1;
    HashMap<Integer, User> users = new HashMap<>();
    Set<String> emails = new HashSet<>();

    public Optional<User> getByUserId(Integer userId) {
        User user = users.get(userId);
        return Optional.ofNullable(user);
    }

    public User create(UserCreateDto userCreateDto) {
        User user = UserMapper.mapUserCreateToUser(userCreateDto);
        user.setId(getNewId());
        users.put(user.getId(), user);
        emails.add(user.getEmail());
        return user;
    }

    public boolean containsByEmail(String email) {
        return emails.contains(email);
    }

    public User update(UserUpdateDto userUpdateDto) {
        User userUpdate = users.get(userUpdateDto.getId());
        emails.remove(userUpdate.getEmail());
        users.remove(userUpdateDto.getId());

        User user = UserMapper.mapUserUpdateDtoToUser(userUpdateDto);
        users.put(user.getId(), user);
        emails.add(user.getEmail());
        return user;
    }

    public Boolean delete(Integer userId) {
        Boolean res = users.containsKey(userId);
        User userDelete = users.get(userId);
        emails.remove(userDelete.getEmail());
        users.remove(userId);
        return res;
    }

    private Integer getNewId() {
        return id++;
    }
}
