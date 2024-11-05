package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.user.User;

import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@ToString
public class ItemWithCommentsDto {

    private Long id;
    private String name;
    private String description;
    private User owner;
    private Boolean available;

    private List<CommentShowDto> comments;
    private Booking lastBooking;
    private Booking nextBooking;

}
