package ru.yandex.practicum.filmorate.serviceTest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class FilmServiceTest {
    private FilmService filmService;

    @Autowired
    public FilmServiceTest(FilmService filmService) {
        this.filmService = filmService;
        for (int i = 0; i <= 10; i++) {
            Film film = new Film();
            film.setName("film" + i);
            film.setDuration(30);
            film.setReleaseDate(LocalDate.now());
            film.setDescription("description");
            filmService.getFilmStorage().addFilm(film);
        }
        for (int i = 1; i < 10; i++) {
            filmService.getUserStorage()
                    .addUser(new User("new@email.com", "login" + i, "name", LocalDate.now().minusDays(10)));
        }
    }

    @BeforeEach
    public void deleteLikes() {
        filmService.getFilmStorage().getModel()
                .forEach(film -> film.getLikes().clear());
    }

    @Test
    @Order(1)
    public void canAddLike() {
        filmService.addLike(1L, 2L);
        assertEquals(1, filmService.getFilmStorage().getById(1L).getLikes().size());
    }

    @Test
    @Order(2)
    public void canDeleteLike() {
        filmService.addLike(2L, 2L);
        filmService.deleteLike(2L, 2L);
        assertEquals(0, filmService.getFilmStorage().getById(2L).getLikes().size());
    }

    @Test
    @Order(3)
    public void canNotAddLikeToNotExistingFilm() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> filmService.addLike(300L, 2L)
        );

        assertEquals("There are no such film with id 300", exception.getMessage());

    }

    @Test
    @Order(4)
    public void canNotAddLike2TimesFor1User() {
        filmService.addLike(3L, 3L);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> filmService.addLike(3L, 3L)
        );

        assertEquals("User with id 3 already added like", exception.getMessage());
    }

    @Test
    @Order(5)
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
    @Order(6)
    public void showsMostPopularForCount() {
        setUpLikes();
        List<Film> films = filmService.mostPopular(5L);
        assertEquals(9, films.getFirst().getLikes().size());
        assertEquals(8, films.get(1).getLikes().size());
        assertEquals(7, films.get(2).getLikes().size());
        assertEquals(5, films.size());
    }

}
