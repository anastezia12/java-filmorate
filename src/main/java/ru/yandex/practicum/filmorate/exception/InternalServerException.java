package ru.yandex.practicum.filmorate.exception;

import org.springframework.dao.DataAccessException;

public class InternalServerException extends DataAccessException {
    public InternalServerException(String msg) {
        super(msg);
    }

    public InternalServerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}