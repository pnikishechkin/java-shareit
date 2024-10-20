package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByBookerIdOrderByStartDesc(Integer bookerId);

    List<Booking> findByBookerIdAndEndIsBeforeOrderByStartDesc(Integer bookerId, LocalDateTime date);

    List<Booking> findByBookerIdAndStartIsAfterOrderByStartDesc(Integer bookerId, LocalDateTime date);

    List<Booking> findByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(Integer bookerId, LocalDateTime start,
                                                                              LocalDateTime end);

    List<Booking> findByBookerIdAndStatusOrderByStartDesc(Integer bookerId, BookingStatus status);

    List<Booking> findByItemIdInOrderByStartDesc(Collection<Integer> items);

    List<Booking> findByItemIdInAndEndIsBeforeOrderByStartDesc(Collection<Integer> items, LocalDateTime date);

    List<Booking> findByItemIdInAndStartIsAfterOrderByStartDesc(Collection<Integer> items, LocalDateTime date);

    List<Booking> findByItemIdInAndStartIsBeforeAndEndIsAfterOrderByStartDesc(Collection<Integer> items, LocalDateTime start,
                                                                              LocalDateTime end);

    List<Booking> findByItemIdInAndStatusOrderByStartDesc(Collection<Integer> items, BookingStatus status);

    Optional<Booking> findByBookerIdAndItemId(Integer bookerId, Integer itemId);

    List<Booking> findByItemIdAndEndIsBeforeOrderByStartDesc(Integer itemId, LocalDateTime start);

    List<Booking> findByItemIdAndStartIsAfterOrderByStartAsc(Integer itemId, LocalDateTime start);
}
