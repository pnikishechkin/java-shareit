package ru.practicum.shareit.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@ToString
public class UserUpdateDto {
    private Long id;
    private String email;
    private String name;
}
