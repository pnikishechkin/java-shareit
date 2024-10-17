package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.dto.CommentShowDto;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.dto.ItemWithCommentsDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemMapper {

    public static Item toEntity(ItemCreateDto itemCreateDto) {
        Item dto = new Item();
        dto.setName(itemCreateDto.getName());
        dto.setDescription(itemCreateDto.getDescription());
        dto.setAvailable(itemCreateDto.getAvailable());
        dto.setOwner(itemCreateDto.getOwner());
        return dto;
    }

    public static Item toEntity(ItemUpdateDto itemUpdateDto) {
        Item dto = new Item();
        dto.setId(itemUpdateDto.getId());
        dto.setName(itemUpdateDto.getName());
        dto.setDescription(itemUpdateDto.getDescription());
        dto.setAvailable(itemUpdateDto.getAvailable());
        dto.setOwner(itemUpdateDto.getOwner());
        return dto;
    }

    public static ItemWithCommentsDto toDto(Item item, List<CommentShowDto> comments, Booking lastBooking,
                                            Booking nextBooking) {
        ItemWithCommentsDto dto = new ItemWithCommentsDto();
        dto.setId(item.getId());
        dto.setAvailable(item.getAvailable());
        dto.setName(item.getName());
        dto.setOwner(item.getOwner());
        dto.setDescription(item.getDescription());

        dto.setComments(comments);
        dto.setLastBooking(lastBooking);
        dto.setNextBooking(nextBooking);

        return dto;
    }

}
