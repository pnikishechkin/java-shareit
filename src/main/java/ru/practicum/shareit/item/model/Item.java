package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.practicum.shareit.user.model.User;

@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Item {
    private Integer id;
    private String name;
    private String description;
    private User owner;
    private Boolean available;
}
