package ru.practicum.shareit.user.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserCreateDto {
    private String email;
    private String name;
}
