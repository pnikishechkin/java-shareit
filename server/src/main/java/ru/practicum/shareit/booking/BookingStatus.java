package ru.practicum.shareit.booking;

public enum BookingStatus {
    /**
     * Новое бронирование
     */
    WAITING,

    /**
     * Бронирование подтверждено владельцем
     */
    APPROVED,

    /**
     * Бронирование отклонено владельцем
     */
    REJECTED,

    /**
     * Бронирование отменено создателем
     */
    CANCELED
}
