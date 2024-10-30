package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingState;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @GetMapping
    public List<Booking> getBookings(@RequestHeader(SHARER_USER_ID) Long userId,
                                     @RequestParam(required = false, defaultValue = "ALL") String state) {
        return bookingService.getBookingsByBooker(userId, BookingState.valueOf(state.toUpperCase()));
    }

    @PostMapping
    public Booking createBooking(@RequestHeader(SHARER_USER_ID) Long userId,
                                 @RequestBody BookingCreateDto bookingCreateDto) {
        bookingCreateDto.setBookerId(userId);
        return bookingService.create(bookingCreateDto);
    }

    @GetMapping("/{bookingId}")
    public Booking getBooking(@RequestHeader(SHARER_USER_ID) Long userId,
                              @PathVariable Long bookingId) {
        return bookingService.getById(userId, bookingId);
    }

    @PatchMapping("/{bookingId}")
    public Booking approveBooking(@RequestHeader(SHARER_USER_ID) Long userId,
                                  @PathVariable Long bookingId,
                                  @RequestParam Boolean approved) {
        return bookingService.approve(userId, bookingId, approved);
    }

    @GetMapping("/owner")
    public List<Booking> getBookingsByUserItems(@RequestHeader(SHARER_USER_ID) Long userId,
                                                @RequestParam(required = false, defaultValue = "ALL") String state) {
        return bookingService.getBookingsByOwner(userId, BookingState.valueOf(state.toUpperCase()));
    }

}
