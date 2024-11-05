package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class BookingCreateDto {

    private Long bookerId;
    private Long itemId;
    private String status;

    private LocalDateTime start;

    private LocalDateTime end;
}
