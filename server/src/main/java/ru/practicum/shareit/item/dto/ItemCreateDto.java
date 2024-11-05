package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ItemCreateDto {
    private String name;
    private String description;

    private Long ownerId;
    private Long requestId;

    private Boolean available;
}
