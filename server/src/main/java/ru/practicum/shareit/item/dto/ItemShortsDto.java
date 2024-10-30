package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ItemShortsDto {
    private Long id;
    private String name;
    private Long ownerId;
}
