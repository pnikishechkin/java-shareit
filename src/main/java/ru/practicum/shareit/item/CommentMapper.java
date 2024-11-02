package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentShowDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static Comment toEntity(CommentCreateDto commentCreateDto) {
        Comment entity = new Comment();
        entity.setItem(commentCreateDto.getItem());
        entity.setText(commentCreateDto.getText());
        entity.setCreated(commentCreateDto.getCreated());
        entity.setAuthor(commentCreateDto.getAuthor());
        return entity;
    }

    public static CommentShowDto toDto(Comment comment) {
        CommentShowDto dto = new CommentShowDto();
        dto.setId(comment.getId());
        dto.setAuthorName(comment.getAuthor().getName());
        dto.setText(comment.getText());
        dto.setCreated(comment.getCreated());
        return dto;
    }
}
