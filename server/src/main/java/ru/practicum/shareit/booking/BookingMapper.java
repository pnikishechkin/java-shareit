package ru.practicum.shareit.booking;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingCreateDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingMapper {

    public static Booking toEntity(BookingCreateDto bookingCreateDto) {
        Booking dto = new Booking();
        dto.setStatus(BookingStatus.WAITING);
        dto.setStart(bookingCreateDto.getStart());
        dto.setEnd(bookingCreateDto.getEnd());
        return dto;
    }
}
