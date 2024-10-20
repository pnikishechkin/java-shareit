package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.practicum.shareit.user.User;

@Data
@EqualsAndHashCode(of = "id")
@ToString
public class ItemUpdateDto {
    @NotNull
    private Integer id;
    private String name;
    private String description;
    private Integer ownerId;
    private User owner;

    @NotNull
    private Boolean available;
}
