package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.practicum.shareit.user.model.User;

@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Item {
    @NotNull
    private Integer id;
    @NotBlank(message = "Название вещи не может быть пустым")
    private String name;
    @NotBlank(message = "Описание вещи не может быть пустым")
    private String description;
    @NotNull
    private User owner;
    @NotNull
    private Boolean available;
}
