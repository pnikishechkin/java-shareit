package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {

    private static final String SHARER_USER_ID = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @PostMapping
    public Booking createBooking(@RequestHeader(SHARER_USER_ID) Integer userId,
                                 @Valid @RequestBody BookingCreateDto bookingCreateDto) {
        bookingCreateDto.setBookerId(userId);
        return bookingService.create(bookingCreateDto);
    }

    @PatchMapping("/{bookingId}")
    public Booking approveBooking(@RequestHeader(SHARER_USER_ID) Integer userId,
                                  @PathVariable Integer bookingId,
                                  @RequestParam Boolean approved) {
        return bookingService.approve(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public Booking getBooking(@RequestHeader(SHARER_USER_ID) Integer userId,
                              @PathVariable Integer bookingId) {
        return bookingService.getById(userId, bookingId);
    }

    @GetMapping
    public List<Booking> getBookingsByBooker(@RequestHeader(SHARER_USER_ID) Integer userId,
                                             @RequestParam(required = false, defaultValue = "ALL") String state) {
        return bookingService.getBookingsByBooker(userId, BookingState.valueOf(state.toUpperCase()));
    }

    @GetMapping("/owner")
    public List<Booking> getBookingsByUserItems(@RequestHeader(SHARER_USER_ID) Integer userId,
                                                @RequestParam(required = false, defaultValue = "ALL") String state) {
        return bookingService.getBookingsByOwner(userId, BookingState.valueOf(state.toUpperCase()));
    }

}
