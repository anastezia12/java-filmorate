package ru.yandex.practicum.filmorate.storageTest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.FilmGenreRepository;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.dal.MpaRepository;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

@JdbcTest
@AutoConfigureTestDatabase
@Import({
        FilmDbStorage.class,
        FilmRepository.class,
        FilmGenreRepository.class,
        GenreRepository.class,
        MpaRepository.class,
        FilmRowMapper.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {
/*
 Я начала писать этот тест и из-за того что @Import({
        FilmDbStorage.class,
        FilmRepository.class,
        FilmGenreRepository.class,
        GenreRepository.class,
        MpaRepository.class,
        FilmRowMapper.class
}) принимает слишком много роу мапперов оно ломается, как это решить?

 */
//    private final FilmDbStorage filmDbStorage;
//
//    @BeforeEach
//    void setUp(){
//        filmDbStorage.clear();
//        for(int i = 0; i < 5 ; i ++){
//            Film film = new Film();
//            film.setMpa(new MPA(1L, "G"));
//            film.setName("name" + i);
//            film.setDescription("desc" + i);
//            film.setReleaseDate(LocalDate.now());
//            film.setDuration(30 + i);
//            filmDbStorage.addFilm(film);
//        }
//    }
//
//    @Test
//    void testAddFilm(){
//        Film film = new Film();
//        film.setMpa(new MPA(1L, "G"));
//        film.setName("name" );
//        film.setDescription("desc" );
//        film.setReleaseDate(LocalDate.now());
//        film.setDuration(30 );
//        Film added = filmDbStorage.addFilm(film);
//        assertThat(added)
//                .isNotNull()
//                .hasFieldOrPropertyWithValue("id", 6L)
//                .hasFieldOrPropertyWithValue("name", "name")
//                .hasFieldOrPropertyWithValue("duration", 30);
//    }
}
