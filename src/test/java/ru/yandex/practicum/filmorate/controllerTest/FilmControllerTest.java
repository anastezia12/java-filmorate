package ru.yandex.practicum.filmorate.controllerTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmControllerTest {
    private static Film film;
    private FilmController filmController;
    private FilmStorage filmStorage;

    @BeforeAll
    public static void filmSetUp() {
        film = new Film();
        film.setName("film");
        film.setDuration(30);
        film.setReleaseDate(LocalDate.now());
        film.setDescription("description");
    }

    @BeforeEach
    public void setUp() {
        filmStorage = new InMemoryFilmStorage();
        filmController = new FilmController(filmStorage, new FilmService(filmStorage, new InMemoryUserStorage()));
    }

    @Test
    public void canAddCorrectFilm() {
        Film postedFilm = filmController.addFilm(film);
        film.setId(1L);
        assertEquals(film, postedFilm);
    }

    @Test
    public void canUpdateExistingFilm() {
        filmController.addFilm(film);
        film.setId(1L);
        film.setName("New film");
        assertEquals(film, filmController.updateFilm(film));
    }

    @Test
    public void canReturnEmptyListWhenNoFilms() {
        assertTrue(filmController.getAll().isEmpty());
    }

    @Test
    public void canReturnFilmsWithMultipleFilms() {
        filmController.addFilm(film);
        filmController.addFilm(film);
        filmController.addFilm(film);
        assertEquals(3, filmController.getAll().size());
        assertTrue(filmController.getAll().stream().allMatch(x -> x.getName().equals("film")));
    }
}
