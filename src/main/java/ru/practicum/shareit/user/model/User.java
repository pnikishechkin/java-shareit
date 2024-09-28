package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * TODO Sprint add-controllers.
 */
@Data
@EqualsAndHashCode(of = "id")
@ToString
public class User {
    @NotNull
    private Integer id;
    @NotBlank(message = "Электронная почта должна быть задана")
    @Email
    private String email;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
}
