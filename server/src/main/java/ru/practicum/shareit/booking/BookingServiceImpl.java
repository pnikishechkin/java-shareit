package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;

    private final UserService userService;
    private final ItemService itemService;

    @Override
    public Booking create(BookingCreateDto bookingCreateDto) {

        if (bookingCreateDto.getStart().equals(bookingCreateDto.getEnd())) {
            throw new BadRequestException("Ошибка! Дата начала и окончания бронирования совпадают");
        }

        User user = userService.getById(bookingCreateDto.getBookerId());

        Item item = itemService.getById(bookingCreateDto.getItemId());
        if (!item.getAvailable()) {
            throw new BadRequestException("Ошибка! Вещь недоступна для бронирования");
        }

        Booking booking = BookingMapper.toEntity(bookingCreateDto);
        booking.setBooker(user);
        booking.setItem(item);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking approve(Long userId, Long bookingId, Boolean approve) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Бронирования с заданным идентификатором не существует"));

        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new BadRequestException("Ошибка! Некорректный пользователь");
        }

        if (approve) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }

        return bookingRepository.save(booking);
    }

    @Override
    public Booking getById(Long userId, Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Бронирования с заданным идентификатором не существует"));

        if (!booking.getItem().getOwner().getId().equals(userId) &&
                !booking.getBooker().getId().equals(userId)) {
            throw new BadRequestException("Ошибка! Некорректный пользователь");
        }

        return booking;
    }

    @Override
    public List<Booking> getBookingsByBooker(Long userId, BookingState state) {

        userService.getById(userId);

        return switch (state) {
            case ALL -> bookingRepository.findByBookerIdOrderByStartDesc(userId);
            case PAST -> bookingRepository.findByBookerIdAndEndIsBeforeOrderByStartDesc(userId, LocalDateTime.now());
            case FUTURE -> bookingRepository.findByBookerIdAndStartIsAfterOrderByStartDesc(userId, LocalDateTime.now());
            case CURRENT -> bookingRepository.findByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(userId,
                    LocalDateTime.now(), LocalDateTime.now());
            case WAITING -> bookingRepository.findByBookerIdAndStatusOrderByStartDesc(userId, BookingStatus.WAITING);
            case REJECTED -> bookingRepository.findByBookerIdAndStatusOrderByStartDesc(userId, BookingStatus.REJECTED);
        };
    }

    @Override
    public List<Booking> getBookingsByOwner(Long userId, BookingState state) {

        User user = userService.getById(userId);

        List<Long> ownerItemsIds = itemRepository.findByOwnerId(user.getId()).stream().map(Item::getId).toList();

        return switch (state) {
            case ALL -> bookingRepository.findByItemIdInOrderByStartDesc(ownerItemsIds);
            case PAST ->
                    bookingRepository.findByItemIdInAndEndIsBeforeOrderByStartDesc(ownerItemsIds, LocalDateTime.now());
            case FUTURE ->
                    bookingRepository.findByItemIdInAndStartIsAfterOrderByStartDesc(ownerItemsIds, LocalDateTime.now());
            case CURRENT -> bookingRepository.findByItemIdInAndStartIsBeforeAndEndIsAfterOrderByStartDesc(ownerItemsIds,
                    LocalDateTime.now(), LocalDateTime.now());
            case WAITING ->
                    bookingRepository.findByItemIdInAndStatusOrderByStartDesc(ownerItemsIds, BookingStatus.WAITING);
            case REJECTED ->
                    bookingRepository.findByItemIdInAndStatusOrderByStartDesc(ownerItemsIds, BookingStatus.REJECTED);
        };
    }
}
