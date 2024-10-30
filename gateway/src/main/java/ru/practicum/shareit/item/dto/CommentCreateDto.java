package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class CommentCreateDto {
    private String text;
    private Integer authorId;
    private Integer itemId;
    private LocalDateTime created;
}
