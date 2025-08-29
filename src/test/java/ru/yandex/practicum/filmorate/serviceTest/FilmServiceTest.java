package ru.yandex.practicum.filmorate.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmServiceTest {
    private FilmStorage filmStorage = new InMemoryFilmStorage();
    private InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
    private FilmService filmService = new FilmService(filmStorage, inMemoryUserStorage);

    @BeforeEach
    public void setUpFilmStorage() {
        for (int i = 0; i <= 10; i++) {
            Film film = new Film();
            film.setName("film" + i);
            film.setDuration(30);
            film.setReleaseDate(LocalDate.now());
            film.setDescription("description");
            filmStorage.addFilm(film);
        }
        for (int i = 1; i < 10; i++) {
            inMemoryUserStorage.addUser(new User("new@email.com", "login" + i, "name", LocalDate.now().minusDays(10)));
        }
    }

    @Test
    public void canAddLike() {
        filmService.addLike(1L, 2L);
        assertEquals(1, filmStorage.getById(1L).getLikes().size());
    }

    @Test
    public void canDeleteLike() {
        filmService.addLike(2L, 2L);
        filmService.deleteLike(2L, 2L);
        assertEquals(0, filmStorage.getById(2L).getLikes().size());
    }

    @Test
    public void canNotAddLikeToNotExistingFilm() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> filmService.addLike(30L, 2L)
        );

        assertEquals("There are no such film with id 30", exception.getMessage());

    }

    @Test
    public void canNotAddLike2TimesFor1User() {
        filmService.addLike(3L, 3L);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> filmService.addLike(3L, 3L)
        );

        assertEquals("User with id 3 already added like", exception.getMessage());
    }

    @Test
    public void canNotDeleteLikeIfItDoNotInLikes() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> filmService.deleteLike(4L, 4L)
        );

        assertEquals("User with id 4 do not have a like here", exception.getMessage());
    }

    public void setUpLikes() {
        for (Long i = 1L; i < 10; i++) {
            filmService.addLike(5L, i);
        }
        for (Long i = 1L; i < 9; i++) {
            filmService.addLike(7L, i);
        }
        for (Long i = 1L; i < 8; i++) {
            filmService.addLike(2L, i);
        }
        for (Long i = 1L; i < 7; i++) {
            filmService.addLike(3L, i);
        }
        for (Long i = 1L; i < 6; i++) {
            filmService.addLike(10L, i);
        }
    }

    @Test
    public void showsMostPopularForCount() {
        setUpLikes();
        List<Film> films = filmService.mostPopular(5L);
        assertEquals(9, films.getFirst().getLikes().size());
        assertEquals(8, films.get(1).getLikes().size());
        assertEquals(7, films.get(2).getLikes().size());
        assertEquals(5, films.size());
    }

    @Test
    public void shows10MostPopularIfThereAreNoCount() {
        setUpLikes();
        List<Film> films = filmService.mostPopular(null);
        assertEquals(9, films.getFirst().getLikes().size());
        assertEquals(8, films.get(1).getLikes().size());
        assertEquals(7, films.get(2).getLikes().size());
        assertEquals(0, films.getLast().getLikes().size());
        assertEquals(10, films.size());
    }

}
