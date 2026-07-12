package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("ru.practicum.shareit")
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAllUncaughtException(Exception e) {

        return new ErrorResponse("INTERNAL_SERVER_ERROR", "Возникла внутренняя ошибка сервера: " + e.getMessage());
    }

    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleBookingNotFoundException(BookingNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), "Бронирование не найдено: " + e.getMessage());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateEmailException(DuplicateEmailException e) {
        return new ErrorResponse("DUPLICATE_EMAIL", "Такой Email уже существует: " + e.getMessage());
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenOperationException(ForbiddenOperationException e) {
        return new ErrorResponse(HttpStatus.FORBIDDEN.getReasonPhrase(), "Запрещенная операция: " + e.getMessage());

    }

    @ExceptionHandler(ItemNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleItemNotAvailableException(ItemNotAvailableException e) {
        return new ErrorResponse(HttpStatus.FORBIDDEN.getReasonPhrase(), "Вещь не доступна: " + e.getMessage());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleItemNotFoundException(ItemNotFoundException e) {
        return new ErrorResponse("NOT_FOUND", "Вещь не найдена: " + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Произошла ошибка при валидации: " + e.getMessage());
    }

    @ExceptionHandler(UnknownStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUnknownStateException(UnknownStateException e) {

        return new ErrorResponse(HttpStatus.BAD_REQUEST.name(), "Ошибка при маппинге статуса бронирования: " + e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        return new ErrorResponse("USER_NOT_FOUND", "Пользователь не найден или не существует: " + e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Произошла ошибка при валидации: " + e.getMessage());
    }
}
