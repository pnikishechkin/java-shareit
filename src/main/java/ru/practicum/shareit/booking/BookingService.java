package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingCreateDto;

import java.util.List;


public interface BookingService {
    Booking getById(Integer userId, Integer bookingId);

    Booking create(BookingCreateDto bookingCreateDto);

    Booking approve(Integer userId, Integer bookingId, Boolean approve);

    List<Booking> getBookingsByBooker(Integer userId, BookingState state);

    List<Booking> getBookingsByOwner(Integer userId, BookingState state);
}
