package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.practicum.shareit.user.model.User;

@Data
@EqualsAndHashCode(of = "id")
@ToString
public class ItemUpdateDto {
    @NotNull
    private Integer id;
    private String name;
    private String description;
    private User owner;
    private Boolean available;
}
