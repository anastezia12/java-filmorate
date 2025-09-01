package ru.yandex.practicum.filmorate.controllerTest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class FilmControllerTest {
    private static Film film;
    private FilmController filmController;

    @BeforeAll
    public static void filmSetUp() {
        film = new Film();
        film.setName("film");
        film.setDuration(30);
        film.setReleaseDate(LocalDate.now());
        film.setDescription("description");
    }

    @Autowired
    public void setUp(FilmController filmController) {
        this.filmController = filmController;
    }

    @Test
    @Order(3)
    public void canAddCorrectFilm() {
        int size = filmController.getAll().size();
        filmController.addFilm(film);
        assertEquals(size + 1, filmController.getAll().size());
    }

    @Test
    @Order(4)
    public void canUpdateExistingFilm() {
        filmController.addFilm(film);
        film.setId(1L);
        film.setName("New film");
        assertEquals(film, filmController.updateFilm(film));
    }

    @Test
    @Order(1)
    public void canReturnEmptyListWhenNoFilms() {
        assertTrue(filmController.getAll().isEmpty());
    }

    @Test
    @Order(2)
    public void canReturnFilmsWithMultipleFilms() {
        filmController.addFilm(film);
        filmController.addFilm(film);
        filmController.addFilm(film);
        assertEquals(3, filmController.getAll().size());
        assertTrue(filmController.getAll().stream().allMatch(x -> x.getName().equals("film")));
    }
}
