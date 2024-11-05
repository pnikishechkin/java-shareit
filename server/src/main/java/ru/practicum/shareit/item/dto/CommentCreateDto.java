package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class CommentCreateDto {
    private String text;
    private Long authorId;
    private Long itemId;
    private LocalDateTime created;
}
