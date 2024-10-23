package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestWithResponsesDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserService userService;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequest create(ItemRequestCreateDto itemRequestCreateDto) {

        User user = userService.getById(itemRequestCreateDto.getAuthorId());

        ItemRequest itemRequest = ItemRequestMapper.toEntity(itemRequestCreateDto);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setAuthor(user);

        return itemRequestRepository.save(itemRequest);
    }

    @Override
    public List<ItemRequestWithResponsesDto> getByUserId(Integer userId) {
        List<ItemRequest> list = itemRequestRepository.findByAuthorId(userId);
        return list.stream().map(this::itemRequestAddResponses).toList();
    }

    @Override
    public ItemRequest getById(Integer itemRequestId) {
        return itemRequestRepository.findById(itemRequestId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Запроса с заданным идентификатором не существует"));
    }

    @Override
    public ItemRequestWithResponsesDto getByIdWithResponses(Integer itemRequestId) {
        ItemRequest itemRequest = itemRequestRepository.findById(itemRequestId).orElseThrow(() -> new NotFoundException(
                "Ошибка! Запроса с заданным идентификатором не существует"));
        return this.itemRequestAddResponses(itemRequest);
    }

    @Override
    public List<ItemRequestWithResponsesDto> getAll(Integer userId) {
        log.debug("Start get all");
        List<ItemRequest> list = itemRequestRepository.findAll();
        return list.stream().map(this::itemRequestAddResponses).toList();
    }

    private ItemRequestWithResponsesDto itemRequestAddResponses(ItemRequest itemRequest) {
        List<Item> items = itemRepository.findByRequestId(itemRequest.getId());
        return ItemRequestMapper.toDto(itemRequest, items);
    }
}
