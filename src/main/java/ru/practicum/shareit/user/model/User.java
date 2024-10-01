package ru.practicum.shareit.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@ToString
public class User {
    private Integer id;
    private String email;
    private String name;
}
