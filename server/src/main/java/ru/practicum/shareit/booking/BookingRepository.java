package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerIdOrderByStartDesc(Long bookerId);

    List<Booking> findByBookerIdAndEndIsBeforeOrderByStartDesc(Long bookerId, LocalDateTime date);

    List<Booking> findByBookerIdAndStartIsAfterOrderByStartDesc(Long bookerId, LocalDateTime date);

    List<Booking> findByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(Long bookerId, LocalDateTime start,
                                                                              LocalDateTime end);

    List<Booking> findByBookerIdAndStatusOrderByStartDesc(Long bookerId, BookingStatus status);

    List<Booking> findByItemIdInOrderByStartDesc(Collection<Long> items);

    List<Booking> findByItemIdInAndEndIsBeforeOrderByStartDesc(Collection<Long> items, LocalDateTime date);

    List<Booking> findByItemIdInAndStartIsAfterOrderByStartDesc(Collection<Long> items, LocalDateTime date);

    List<Booking> findByItemIdInAndStartIsBeforeAndEndIsAfterOrderByStartDesc(Collection<Long> items, LocalDateTime start,
                                                                              LocalDateTime end);

    List<Booking> findByItemIdInAndStatusOrderByStartDesc(Collection<Long> items, BookingStatus status);

    Optional<Booking> findByBookerIdAndItemId(Long bookerId, Long itemId);

    List<Booking> findByItemIdAndEndIsBeforeOrderByStartDesc(Long itemId, LocalDateTime start);

    List<Booking> findByItemIdAndStartIsAfterOrderByStartAsc(Long itemId, LocalDateTime start);
}
