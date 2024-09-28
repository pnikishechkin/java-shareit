package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@ToString
public class UserUpdateDto {
    @NotNull
    private Integer id;
    private String email;
    private String name;
}
