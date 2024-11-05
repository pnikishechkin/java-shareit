package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingState;

import java.util.List;


public interface BookingService {
    Booking getById(Long userId, Long bookingId);

    Booking create(BookingCreateDto bookingCreateDto);

    Booking approve(Long userId, Long bookingId, Boolean approve);

    List<Booking> getBookingsByBooker(Long userId, BookingState state);

    List<Booking> getBookingsByOwner(Long userId, BookingState state);
}
