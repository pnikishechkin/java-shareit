package ru.practicum.shareit.request.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ItemRequestCreateDto {
    private Integer authorId;
    private String description;
}
