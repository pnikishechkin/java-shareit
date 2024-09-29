package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Validated
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Item getById(Integer itemId) {
        return itemRepository.getByItemId(itemId).orElseThrow(() -> new NotFoundException("Ошибка! Вещи с заданным " +
                "идентификатором не существует"));
    }

    @Override
    public Set<Item> getByUserId(Integer userId) {
        return itemRepository.getByUserId(userId).orElseThrow(() -> new NotFoundException("Ошибка! Вещи с " +
                "заданным идентификатором не существует"));
    }

    @Override
    public Item create(ItemCreateDto itemCreateDto) {
        User user = userRepository.getByUserId(itemCreateDto.getOwnerId()).orElseThrow(() -> new NotFoundException
                ("Ошибка! Пользователя с заданным идентификатором не существует"));
        itemCreateDto.setOwner(user);
        return itemRepository.create(itemCreateDto);
    }

    @Override
    public Set<Item> search(String text) {
        return itemRepository.search(text);
    }

    @Override
    public Item update(ItemUpdateDto itemUpdateDto) {
        Item item = itemRepository.getByItemId(itemUpdateDto.getId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Вещи с заданным идентификатором не существует"));
        User user = userRepository.getByUserId(itemUpdateDto.getOwnerId()).orElseThrow(() -> new NotFoundException
                ("Ошибка! Пользователя с заданным идентификатором не существует"));

        if (itemUpdateDto.getAvailable() == null) {
            itemUpdateDto.setAvailable(item.getAvailable());
        }
        if (itemUpdateDto.getName() == null) {
            itemUpdateDto.setName(item.getName());
        }
        if (itemUpdateDto.getDescription() == null) {
            itemUpdateDto.setDescription(item.getDescription());
        }
        itemUpdateDto.setOwner(user);

        return itemRepository.update(itemUpdateDto);
    }
}
