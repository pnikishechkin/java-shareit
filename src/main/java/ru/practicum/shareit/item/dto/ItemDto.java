package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
@ToString
public class ItemDto {
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
