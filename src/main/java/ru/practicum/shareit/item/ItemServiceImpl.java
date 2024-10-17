package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemWithCommentsDto getById(Integer itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Ошибка! Вещи с заданным " +
                "идентификатором не существует"));

        return getCommentsAndBooking(item);
    }

    @Override
    public List<ItemWithCommentsDto> getByUserId(Integer userId) {

        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));
        List<Item> items = itemRepository.findByOwnerId(userId);

        return items.stream().map(this::getCommentsAndBooking).toList();
    }

    @Override
    public Item create(ItemCreateDto itemCreateDto) {

        User user = userRepository.findById(itemCreateDto.getOwnerId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));
        itemCreateDto.setOwner(user);
        return itemRepository.save(ItemMapper.toEntity(itemCreateDto));
    }

    @Override
    public List<Item> search(String text) {
        if (text.isEmpty()) {
            return List.of();
        }
        return itemRepository.search(text);
    }

    @Override
    public Item update(ItemUpdateDto itemUpdateDto) {
        Item item = itemRepository.findById(itemUpdateDto.getId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Вещи с заданным идентификатором не существует"));
        User user = userRepository.findById(itemUpdateDto.getOwnerId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));

        if (itemUpdateDto.getAvailable() == null) {
            itemUpdateDto.setAvailable(item.getAvailable());
        }
        if (itemUpdateDto.getName() == null) {
            itemUpdateDto.setName(item.getName());
        }
        if (itemUpdateDto.getDescription() == null) {
            itemUpdateDto.setDescription(item.getDescription());
        }
        itemUpdateDto.setOwner(user);

        return itemRepository.save(ItemMapper.toEntity(itemUpdateDto));
    }

    @Override
    public CommentShowDto postComment(CommentCreateDto commentCreateDto) {
        Item item = itemRepository.findById(commentCreateDto.getItemId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Вещи с заданным идентификатором не существует"));
        User user = userRepository.findById(commentCreateDto.getAuthorId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Пользователя с заданным идентификатором не существует"));

        Booking booking = bookingRepository.findByBookerIdAndItemId(commentCreateDto.getAuthorId(),
                commentCreateDto.getItemId()).orElseThrow(() -> new NotFoundException(
                "Ошибка! Заказа от данного автора на данную вещь не существовало!"));

        commentCreateDto.setItem(item);
        commentCreateDto.setAuthor(user);

        // Бронирование не было подтверждено или пока не закончено
        if (!booking.getStatus().equals(BookingStatus.APPROVED) || booking.getEnd().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("Невозможно оставить комментарий! Бронирование не было подтверждено или не " +
                    "закончено!");
        }

        commentCreateDto.setCreated(LocalDateTime.now());
        Comment comment = commentRepository.save(CommentMapper.toEntity(commentCreateDto));
        return CommentMapper.toDto(comment);
    }

    private ItemWithCommentsDto getCommentsAndBooking(Item item) {
        List<CommentShowDto> comments =
                commentRepository.findByItemId(item.getId()).stream().map(CommentMapper::toDto).toList();
        List<Booking> list = bookingRepository.findByItemIdAndEndIsBeforeOrderByStartDesc(item.getId(),
                LocalDateTime.now());
        Booking lastBooking = list.isEmpty() ? null : list.getLast();
        list = bookingRepository.findByItemIdAndStartIsAfterOrderByStartAsc(item.getId(),
                LocalDateTime.now());
        Booking nextBooking = list.isEmpty() ? null : list.getFirst();

        return ItemMapper.toDto(item, comments, lastBooking, nextBooking);
    }
}
