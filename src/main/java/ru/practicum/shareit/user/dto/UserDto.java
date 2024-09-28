package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@ToString
public class UserDto {
    @NotNull
    private Integer id;
    @NotBlank(message = "Электронная почта должна быть задана")
    @Email
    private String email;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
}
