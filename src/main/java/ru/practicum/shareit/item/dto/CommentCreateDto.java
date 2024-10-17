package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.ToString;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@ToString
public class CommentCreateDto {
    private String text;
    private Integer authorId;
    private User author;
    private Integer itemId;
    private Item item;
    private LocalDateTime created;
}
