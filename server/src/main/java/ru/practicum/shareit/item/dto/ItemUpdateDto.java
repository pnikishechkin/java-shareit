package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@ToString
public class ItemUpdateDto {
    private Long id;
    private String name;
    private String description;
    private Long ownerId;

    private Boolean available;
}
