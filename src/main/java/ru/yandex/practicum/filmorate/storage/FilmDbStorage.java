package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.FilmGenreRepository;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.dal.MpaRepository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@Component
@Qualifier("filmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final FilmGenreRepository filmGenreRepository;
    private final MpaRepository mpaRepository;

    @Autowired
    public FilmDbStorage(FilmRepository filmRepository,
                         GenreRepository genreRepository,
                         FilmGenreRepository filmGenreRepository,
                         MpaRepository mpaRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
        this.filmGenreRepository = filmGenreRepository;
        this.mpaRepository = mpaRepository;
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        filmRepository.addLike(filmId, userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        filmRepository.deleteLike(filmId, userId);
    }

    @Override
    public List<Long> getAllLikes(Long filmId) {
        return filmRepository.getFilmLikes(filmId);
    }

    @Override
    public Film addFilm(Film film) {
        validateMpa(film.getMpa());
        Film saved = filmRepository.save(film);

        addGenres(film, saved);
        saved.setMpa(mpaRepository.findById(film.getMpa().getId()).get());

        return saved;
    }

    public void addGenres(Film film, Film saved) {
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            List<Genre> genreForFilm = film.getGenres().stream()
                    .map(genre -> genreRepository.getById(genre.getId())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "Genre with id " + genre.getId() + " does not exist")))
                    .distinct()
                    .sorted(Comparator.comparingLong(Genre::getId))
                    .toList();

            List<Long> genreIds = genreForFilm.stream()
                    .map(Genre::getId)
                    .toList();

            saved.setGenres(genreForFilm);
            filmGenreRepository.addGenresToFilm(saved.getId(), genreIds);
        }
    }

    @Override
    public Film updateFilm(Film film) {
        validateMpa(film.getMpa());
        Film updated = filmRepository.update(film);
        addGenres(film, updated);
        updated.setMpa(mpaRepository.findById(film.getMpa().getId()).get());
        return updated;
    }


    @Override
    public boolean containsFilmWithKey(Long id) {
        return filmRepository.findById(id).isPresent();
    }

    @Override
    public Film getById(Long id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Film not found: " + id));

        List<Genre> genres = filmGenreRepository.getGenresForFilm(film.getId());
        film.setGenres(genres);
        List<Long> likes = filmRepository.getFilmLikes(id);
        film.setLikes(new HashSet<>(likes));
        return film;
    }

    @Override
    public List<Film> getModel() {
        List<Long> filmsId = filmRepository.findAll().stream().map(x -> x.getId()).toList();
        List<Film> films = new ArrayList<>();
        for (Long i : filmsId) {
            films.add(getById(i));
        }
        return films;
    }

    @Override
    public void deleteById(Long id) {
        filmRepository.deleteById(id);
    }

    @Override
    public void clear() {
        /*В задании написано что
         " Резидентная база данных обеспечит автономность ваших интеграционных тестов —
         перед каждым их запуском Spring будет создавать новую, чистую БД."
         Но у меня ничего не удаляется в тестах, поэтому остался этот метод
         Помогите мне сделать так чтобы бд очищалась без этого, а то как-то
         не очень безопасно
         */
        filmRepository.clear();
    }

    private void validateMpa(MPA mpa) {
        if (mpa == null || mpa.getId() == null) {
            throw new IllegalArgumentException("MPA rating cannot be null");
        }
        boolean exists = mpaRepository.findById(mpa.getId()).isPresent();
        if (!exists) {
            throw new IllegalArgumentException("Unknown MPA id: " + mpa.getId());
        }
    }
}

