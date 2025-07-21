package ru.yandex.practicum.filmorate.controllerTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmControllerTest {
    private static Film film;
    private FilmController filmController;

    @BeforeAll
    public static void filmSetUp() {
        film = new Film();
        film.setName("film");
        film.setDuration(Duration.ofMinutes(30));
        film.setReleaseDate(LocalDate.now());
        film.setDescription("description");
    }

    @BeforeEach
    public void setUp() {
        filmController = new FilmController();
    }

    @Test
    public void canAddCorrectFilm() {
        Film postedFilm = filmController.post(film);
        film.setId(1L);
        assertEquals(film, postedFilm);
    }

    @Test
    public void canUpdateExistingFilm() {
        filmController.post(film);
        film.setId(1L);
        film.setName("New film");
        assertEquals(film, filmController.update(film));
    }

    @Test
    public void canReturnEmptyListWhenNoFilms() {
        assertTrue(filmController.getAll().isEmpty());
    }

    @Test
    public void canReturnFilmsWithMultipleFilms() {
        filmController.post(film);
        filmController.post(film);
        filmController.post(film);
        assertEquals(3, filmController.getAll().size());
        assertTrue(filmController.getAll().stream().allMatch(x -> x.getName().equals("film")));
    }
}
