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
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;

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

    private final UserService userService;
    private final ItemRequestService itemRequestService;

    @Override
    public ItemWithCommentsDto getDtoById(Integer itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Ошибка! Вещи с заданным " +
                "идентификатором не существует"));
        return getCommentsAndBooking(item);
    }

    @Override
    public Item getById(Integer itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Вещи с заданным идентификатором не существует"));
    }

    @Override
    public List<ItemWithCommentsDto> getByUserId(Integer userId) {

        userService.getById(userId);
        List<Item> items = itemRepository.findByOwnerId(userId);
        return items.stream().map(this::getCommentsAndBooking).toList();
    }

    @Override
    public Item create(ItemCreateDto itemCreateDto) {

        User user = userService.getById(itemCreateDto.getOwnerId());
        Item item = ItemMapper.toEntity(itemCreateDto);
        item.setOwner(user);

        if (itemCreateDto.getRequestId() != null) {
            ItemRequest request = itemRequestService.getById(itemCreateDto.getRequestId());
            item.setRequest(request);
        }

        return itemRepository.save(item);
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

        User user = userService.getById(itemUpdateDto.getOwnerId());

        if (itemUpdateDto.getAvailable() == null) {
            itemUpdateDto.setAvailable(item.getAvailable());
        }
        if (itemUpdateDto.getName() == null) {
            itemUpdateDto.setName(item.getName());
        }
        if (itemUpdateDto.getDescription() == null) {
            itemUpdateDto.setDescription(item.getDescription());
        }

        Item updateItem = ItemMapper.toEntity(itemUpdateDto);
        updateItem.setOwner(user);

        return itemRepository.save(updateItem);
    }

    @Override
    public CommentShowDto postComment(CommentCreateDto commentCreateDto) {
        Item item = getById(commentCreateDto.getItemId());
        User user = userService.getById(commentCreateDto.getAuthorId());

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

        return ItemMapper.toDto(item, comments, null, nextBooking);
    }

}
