package ru.practicum.shareit.exception;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ErrorHandlerTest {

    private final ErrorHandler errorHandler;
    private NotFoundException notFoundException;
    private AlreadyExistException alreadyExistException;
    private BadRequestException badRequestException;

    @BeforeEach
    void init() {
        notFoundException = new NotFoundException("message");
        alreadyExistException = new AlreadyExistException("message");
        badRequestException = new BadRequestException("message");
    }

    @Test
    void testNotFoundException() {
        ErrorResponse errorResponse = errorHandler.handleNotFoundException(notFoundException);
        assertEquals(errorResponse.getError(), "message");
    }

    @Test
    void testAlreadyExistException() {
        ErrorResponse errorResponse = errorHandler.handleConflictException(alreadyExistException);
        assertEquals(errorResponse.getError(), "message");
    }

    @Test
    void testBadRequestException() {
        ErrorResponse errorResponse = errorHandler.handleBadRequestException(badRequestException);
        assertEquals(errorResponse.getError(), "message");
    }

    @Test
    void testHandleException() {
        ErrorResponse errorResponse = errorHandler.handleException(new RuntimeException("message"));
        assertEquals(errorResponse.getError(), "message");
    }

}