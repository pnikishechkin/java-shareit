package ru.practicum.shareit.request.dto;

import lombok.Data;
import lombok.ToString;
import ru.practicum.shareit.item.dto.ItemShortsDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
public class ItemRequestWithResponsesDto {
    private Integer id;
    private LocalDateTime created;
    private String description;
    private List<ItemShortsDto> items;
}
