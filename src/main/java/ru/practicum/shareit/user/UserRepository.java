package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}

/*
@Repository
public class UserRepository {
    private static Integer id = 1;
    private final HashMap<Integer, User> users = new LinkedHashMap<>();
    private final Set<String> emails = new LinkedHashSet<>();

    public Optional<User> getByUserId(Integer userId) {
        User user = users.get(userId);
        return Optional.ofNullable(user);
    }

    public User create(UserCreateDto userCreateDto) {
        User user = UserMapper.toEntity(userCreateDto);
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

        User user = UserMapper.toEntity(userUpdateDto);
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

 */
