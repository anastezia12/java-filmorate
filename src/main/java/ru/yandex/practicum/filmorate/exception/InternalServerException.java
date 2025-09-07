package ru.yandex.practicum.filmorate.exception;

import org.springframework.dao.DataAccessException;

public class InternalServerException extends DataAccessException {
    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }
}