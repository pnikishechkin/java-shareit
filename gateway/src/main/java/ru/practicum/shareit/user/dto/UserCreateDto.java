package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserCreateDto {
    @NotBlank(message = "Электронная почта должна быть задана")
    @Email
    private String email;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
}
