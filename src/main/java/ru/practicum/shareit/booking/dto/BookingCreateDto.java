package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@ToString
public class BookingCreateDto {

    private Integer bookerId;
    private User booker;

    @NotNull
    private Integer itemId;
    private Item item;
    private String status;

    @NotNull
    private LocalDateTime start;

    @NotNull
    @Future
    private LocalDateTime end;
}
