package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class CommentShowDto {
    private Long id;
    private String text;
    private String authorName;
    private LocalDateTime created;
}
