package ru.yandex.practicum.filmorate.storageTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InMemoryFilmStorageTest {
    private Film film = new Film();
    @Autowired
    private InMemoryFilmStorage inMemoryFilmStorage;

    @BeforeEach
    public void resetStorage() {
        inMemoryFilmStorage.clear();    // if you use an internal ID generator
    }

    public void srtUp() {
        film.setName("film");
        film.setDuration(30);
        film.setReleaseDate(LocalDate.now());
        film.setDescription("description");
    }

    @Test
    public void canAddFilmToModel() {
        inMemoryFilmStorage.addFilm(film);
        assertEquals(1L, film.getId());
    }

    @Test
    public void canDeleteById() {
        inMemoryFilmStorage.addFilm(film);
        inMemoryFilmStorage.deleteById(1L);
        assertEquals(0, inMemoryFilmStorage.getModel().size());
    }

    @Test
    public void canGetById() {
        inMemoryFilmStorage.addFilm(film);
        assertEquals(film, inMemoryFilmStorage.getById(1L));
    }

    @Test
    public void returnNothingWhenNoSuchFilmWithId() {
        assertNull(inMemoryFilmStorage.getById(1L));
    }

    @Test
    public void canUpdateFilm() {
        inMemoryFilmStorage.addFilm(film);
        Film newFilm = new Film();
        newFilm.setName("Updated");
        newFilm.setId(1L);
        inMemoryFilmStorage.updateFilm(newFilm);
        assertEquals(newFilm.getName(), inMemoryFilmStorage.getById(1L).getName());
    }

    @Test
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
        assertFalse(inMemoryFilmStorage.containsFilmWithKey(1L));
    }

    @Test
    public void willReturnEmptyModelIfNoFilms() {
        assertEquals(0, inMemoryFilmStorage.getModel().size());
    }

    @Test
    public void willReturnModelWithFilms() {
        inMemoryFilmStorage.addFilm(film);
        inMemoryFilmStorage.addFilm(film);
        assertEquals(2, inMemoryFilmStorage.getModel().size());
    }


}
