package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByOwnerId(Integer ownerId);

    @Query("select i from Item i " +
            "where (i.available=true) and upper(i.name) like upper(concat('%', ?1, '%')) " +
            " or upper(i.description) like upper(concat('%', ?1, '%'))")
    List<Item> search(String text);
}

/*
@Repository
public class ItemRepository {
    private static Integer id = 1;
    private final HashMap<Integer, Item> items = new LinkedHashMap<>();
    private final HashMap<Integer, Set<Item>> usersItems = new LinkedHashMap<>();

    public Item create(ItemCreateDto itemCreateDto) {
        Item item = ItemMapper.toEntity(itemCreateDto);
        item.setId(getNewId());
        items.put(item.getId(), item);

        if (!usersItems.containsKey(itemCreateDto.getOwnerId())) {
            usersItems.put(itemCreateDto.getOwnerId(), new LinkedHashSet<>());
        }

        usersItems.get(itemCreateDto.getOwnerId()).add(item);
        return item;
    }

    public Item update(ItemUpdateDto itemUpdateDto) {
        Item itemUpdate = items.get(itemUpdateDto.getId());
//        usersItems.get(itemUpdate.getOwner().getId()).remove(itemUpdate);
        items.remove(itemUpdateDto.getId());

        Item item = ItemMapper.toEntity(itemUpdateDto);
        items.put(item.getId(), item);
        if (!usersItems.containsKey(itemUpdateDto.getOwnerId())) {
            usersItems.put(itemUpdateDto.getOwnerId(), new LinkedHashSet<>());
        }
        usersItems.get(itemUpdateDto.getOwnerId()).add(item);

        return item;
    }

    public Optional<Item> getByItemId(Integer itemId) {
        Item item = items.get(itemId);
        return Optional.ofNullable(item);
    }

    public Optional<Set<Item>> getByUserId(Integer userId) {
        Set<Item> items = usersItems.get(userId);
        return Optional.ofNullable(items);
    }

    public Set<Item> search(String text) {

        if (text.isEmpty())
            return Set.of();

        return null;

//        return items.values().stream()
//                .filter(u -> (u.getAvailable() && (u.getName().toLowerCase().contains(text.toLowerCase())
//                        || u.getDescription().toLowerCase().contains(text.toLowerCase()))))
//                .collect(Collectors.toSet());
    }

    private Integer getNewId() {
        return id++;
    }
}
 */
