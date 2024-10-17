package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Booking create(BookingCreateDto bookingCreateDto) {
        User user = userRepository.findById(bookingCreateDto.getBookerId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));
        bookingCreateDto.setBooker(user);

        if (bookingCreateDto.getStart().equals(bookingCreateDto.getEnd())) {
            throw new BadRequestException("Ошибка! Дата начала и окончания бронирования совпадают");
        }

        Item item = itemRepository.findById(bookingCreateDto.getItemId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Вещи с заданным идентификатором не существует"));
        if (!item.getAvailable()) {
            throw new BadRequestException("Ошибка! Вещь недоступна для бронирования");
        }
        bookingCreateDto.setItem(item);

        return bookingRepository.save(BookingMapper.toEntity(bookingCreateDto));
    }

    @Override
    public Booking approve(Integer userId, Integer bookingId, Boolean approve) {
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
    public Booking getById(Integer userId, Integer bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Бронирования с заданным идентификатором не существует"));

        if (!booking.getItem().getOwner().getId().equals(userId) &&
                !booking.getBooker().getId().equals(userId)) {
            throw new BadRequestException("Ошибка! Некорректный пользователь");
        }

        return booking;
    }

    @Override
    public List<Booking> getBookingsByBooker(Integer userId, BookingState state) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));

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
    public List<Booking> getBookingsByOwner(Integer userId, BookingState state) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));

        List<Integer> ownerItemsIds = itemRepository.findByOwnerId(user.getId()).stream().map(Item::getId).toList();

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
