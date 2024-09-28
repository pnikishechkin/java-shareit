package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Validated
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemDto getById(Integer itemId) {
        return itemRepository.getByItemId(itemId).orElseThrow(() -> new NotFoundException("Ошибка! Вещи с заданным " +
                "идентификатором не существует"));
    }

    public Set<ItemDto> getByUserId(Integer userId) {
        return itemRepository.getByUserId(userId).orElseThrow(() -> new NotFoundException("Ошибка! Вещи с " +
                "заданным идентификатором не существует"));
    }

    public ItemDto create(ItemCreateDto itemCreateDto) {
        // проверка наличия пользователя
        return itemRepository.create(itemCreateDto);
    }

    public Set<ItemDto> search(String text) {
        return itemRepository.search(text);
    }
}
