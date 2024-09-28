package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemRepository {
    private static Integer id = 1;
    HashMap<Integer, ItemDto> items = new HashMap<>();
    HashMap<Integer, Set<ItemDto>> usersItems = new HashMap<>();

    public ItemDto create(ItemCreateDto itemCreateDto) {
        ItemDto itemDto = ItemMapper.mapToItemDto(itemCreateDto);
        itemDto.setId(getNewId());
        items.put(itemDto.getId(), itemDto);

        if (!usersItems.containsKey(itemCreateDto.getOwnerId())) {
            usersItems.put(itemCreateDto.getOwnerId(), new LinkedHashSet<>());
        }

        usersItems.get(itemCreateDto.getOwnerId()).add(itemDto);
        return itemDto;
    }

    public void update(ItemUpdateDto itemUpdateDto) {

    }

    public Optional<ItemDto> getByItemId(Integer itemId) {
        ItemDto item = items.get(itemId);
        return Optional.ofNullable(item);
    }

    public Optional<Set<ItemDto>> getByUserId(Integer userId) {
        Set<ItemDto> items = usersItems.get(userId);
        return Optional.ofNullable(items);
    }

    public Set<ItemDto> search(String text) {
        return items.values().stream()
                .filter(u -> (!u.getAvailable() && (u.getName().contains(text) || u.getDescription().contains(text))))
                .collect(Collectors.toSet());
    }

    private Integer getNewId() {
        return id++;
    }
}
