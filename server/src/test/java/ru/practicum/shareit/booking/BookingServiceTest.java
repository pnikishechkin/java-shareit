package ru.practicum.shareit.booking;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.BaseTestData;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
class BookingServiceTest extends BaseTestData {

    private final BookingService service;

    @Test
    void getById_correctId() {
        Booking testBooking = service.getById(1L, 1L);
        assertEquals(booking1, testBooking);
    }

    @Test
    void getById_notExist() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.getById(1L, 10L));
        assertEquals(ex.getMessage(), "Ошибка! Бронирования с заданным идентификатором не существует");
    }

    @Test
    void getBookingsByBooker_correct() {
        List<Booking> bookings = service.getBookingsByBooker(3L, BookingState.ALL);
        assertEquals(1, bookings.size());
        assertEquals(booking2, bookings.getFirst());

        bookings = service.getBookingsByBooker(3L, BookingState.PAST);
        assertEquals(1, bookings.size());

        bookings = service.getBookingsByBooker(3L, BookingState.CURRENT);
        assertEquals(0, bookings.size());

        bookings = service.getBookingsByBooker(3L, BookingState.WAITING);
        assertEquals(0, bookings.size());

        bookings = service.getBookingsByBooker(3L, BookingState.FUTURE);
        assertEquals(0, bookings.size());

        bookings = service.getBookingsByBooker(3L, BookingState.REJECTED);
        assertEquals(0, bookings.size());
    }

    @Test
    void getBookingsByOwner_correct() {
        List<Booking> bookings = service.getBookingsByOwner(1L, BookingState.ALL);
        assertEquals(1, bookings.size());
        assertEquals(booking1, bookings.getFirst());

        bookings = service.getBookingsByOwner(1L, BookingState.PAST);
        assertEquals(0, bookings.size());

        bookings = service.getBookingsByOwner(1L, BookingState.CURRENT);
        assertEquals(1, bookings.size());

        bookings = service.getBookingsByOwner(1L, BookingState.WAITING);
        assertEquals(1, bookings.size());

        bookings = service.getBookingsByOwner(1L, BookingState.FUTURE);
        assertEquals(0, bookings.size());

        bookings = service.getBookingsByOwner(1L, BookingState.REJECTED);
        assertEquals(0, bookings.size());
    }

    @Test
    void create_correct() {
        Booking newBooking = service.create(bookingCreateDto);
        assertEquals(3L, newBooking.getId());
        assertDoesNotThrow(() -> service.getById(1L, 3L));
    }

    @Test
    void create_sameDate_Exception() {
        bookingCreateDto.setStart(LocalDateTime.of(2024, 10, 31, 10, 0, 0));
        bookingCreateDto.setEnd(LocalDateTime.of(2024, 10, 31, 10, 0, 0));
        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.create(bookingCreateDto));
        assertEquals(ex.getMessage(), "Ошибка! Дата начала и окончания бронирования совпадают");
    }

    @Test
    void create_itemNotAvailable_Exception() {
        bookingCreateDto.setItemId(2L);
        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.create(bookingCreateDto));
        assertEquals(ex.getMessage(), "Ошибка! Вещь недоступна для бронирования");
    }

    @Test
    void approve_correct_approved() {
        service.approve(1L, 1L, true);
        Booking testBooking = service.getById(1L, 1L);
        assertEquals(BookingStatus.APPROVED, testBooking.getStatus());
    }

    @Test
    void approve_correct_notApproved() {
        service.approve(1L, 1L, false);
        Booking testBooking = service.getById(1L, 1L);
        assertEquals(BookingStatus.REJECTED, testBooking.getStatus());
    }

    @Test
    void approve_notCorrectUser() {
        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.approve(2L, 1L, true));
        assertEquals(ex.getMessage(), "Ошибка! Некорректный пользователь");
    }

    @Test
    void approve_notExist() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.approve(1L, 10L, true));
        assertEquals(ex.getMessage(), "Ошибка! Бронирования с заданным идентификатором не существует");
    }
}