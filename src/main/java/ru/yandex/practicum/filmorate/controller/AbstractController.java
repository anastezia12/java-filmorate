package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController<T> {
    protected final Map<Long, T> model = new HashMap<>();
    protected Logger log;

    public AbstractController(Logger log) {
        this.log = log;
    }

    public abstract void setId(Long id, T object);

    public abstract void updateFields(T object);

    public abstract Long getId(T object);


    @GetMapping
    public Collection<T> getAll() {
        return model.values();
    }

    @PostMapping
    public T post(@Valid @RequestBody T object) {
        log.debug("Started adding {}", object.getClass());
        Long id = getNextId();
        setId(id, object);
        model.put(id, object);
        log.info("Successfully added {} with id= {}", object.getClass(), id);
        return object;
    }

    @PutMapping
    public T update(@Valid @RequestBody T object) {
        Long id = getId(object);
        if (id == null) {
            log.warn("UpdateFailed: id = null");
            throw new ValidationException("Id should be present");
        }

        if (!model.containsKey(getId(object))) {
            log.warn("id={} is not found in model", id);
            throw new ValidationException("There are no such id=" + id);
        }
        updateFields(object);
        log.info("Successfully updated {} with id={} ", object.getClass(), id);
        return model.get(id);
    }


    private long getNextId() {
        long currentMaxId = model.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }
}
