package ru.yandex.practicum.filmorate.controllerTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FilmControllerTest {
    private static Film film;
    @Autowired
    private FilmController filmController;
    @Autowired
    @Qualifier("filmDbStorage")
    private FilmStorage filmStorage;

    @BeforeAll
    public static void filmSetUp() {
        film = new Film();
        film.setName("film");
        film.setDuration(30);
        film.setReleaseDate(LocalDate.now());
        film.setDescription("description");
        film.setMpa(new MPA(1L, "G"));
    }

    @BeforeEach
    void reset() {
        filmStorage.clear();
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
