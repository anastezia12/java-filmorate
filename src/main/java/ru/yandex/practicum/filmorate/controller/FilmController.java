package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAll() {
        return filmService.getFilmStorage().getModel();
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable Long id) {
        return filmService.getFilmStorage().getById(id);
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.debug("Started adding {}", film.getClass());
        Film added = filmService.getFilmStorage().addFilm(film);
        log.info("Successfully added {} with id= {}", added.getClass(), added.getId());
        return added;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopular(@RequestParam(required = false, defaultValue = "10") Long count) {
        return filmService.mostPopular(count);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        Long id = film.getId();
        if (id == null) {
            log.warn("UpdateFailed: id = null");
            throw new ValidationException("Id should be present");
        }

        Film updated = filmService.getFilmStorage().updateFilm(film);
        log.info("Successfully updated {} with id={} ", film.getClass(), id);
        return updated;
    }
}
