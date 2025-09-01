package ru.yandex.practicum.filmorate.storageTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class InMemoryFilmStorageTest {
    private Film film = new Film();
    private InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired
    public InMemoryFilmStorageTest(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        film.setName("film");
        film.setDuration(30);
        film.setReleaseDate(LocalDate.now());
        film.setDescription("description");
    }

    @Test
    @Order(2)
    public void canAddFilmToModel() {
        inMemoryFilmStorage.addFilm(film);
        assertEquals(1L, film.getId());
    }

    @Test
    @Order(3)
    public void canDeleteById() {
        inMemoryFilmStorage.addFilm(film);
        inMemoryFilmStorage.deleteById(film.getId());
        assertEquals(1, inMemoryFilmStorage.getModel().size());
    }

    @Test
    @Order(4)
    public void canGetById() {
        inMemoryFilmStorage.addFilm(film);
        assertEquals(film, inMemoryFilmStorage.getById(film.getId()));
    }

    @Test
    @Order(5)
    public void returnNothingWhenNoSuchFilmWithId() {
        assertNull(inMemoryFilmStorage.getById(1000L));
    }

    @Test
    @Order(6)
    public void canUpdateFilm() {
        inMemoryFilmStorage.addFilm(film);
        Film newFilm = new Film();
        newFilm.setName("Updated");
        newFilm.setId(1L);
        inMemoryFilmStorage.updateFilm(newFilm);
        assertEquals(newFilm.getName(), inMemoryFilmStorage.getById(1L).getName());
    }

    @Test
    @Order(7)
    public void willNotUpdateNullFields() {
        inMemoryFilmStorage.addFilm(film);
        Film newFilm = new Film();
        newFilm.setName("Updated");
        newFilm.setId(1L);
        inMemoryFilmStorage.updateFilm(newFilm);
        assertEquals(film.getDescription(), inMemoryFilmStorage.getById(1L).getDescription());
    }

    @Test
    public void containsFilmWithKeyTrue() {
        inMemoryFilmStorage.addFilm(film);
        assertTrue(inMemoryFilmStorage.containsFilmWithKey(1L));
    }

    @Test
    public void containsFilmWithKeyFalse() {
        assertFalse(inMemoryFilmStorage.containsFilmWithKey(100L));
    }

    @Test
    @Order(1)
    public void willReturnEmptyModelIfNoFilms() {
        assertEquals(0, inMemoryFilmStorage.getModel().size());
    }

    @Test
    public void willReturnModelWithFilms() {
        inMemoryFilmStorage.addFilm(film);
        assertTrue(inMemoryFilmStorage.getModel().contains(film));
    }

}
