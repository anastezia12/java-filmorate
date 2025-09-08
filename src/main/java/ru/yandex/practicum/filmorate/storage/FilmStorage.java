package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);

    void deleteById(Long id);

    Film updateFilm(Film film);

    boolean containsFilmWithKey(Long id);

    Film getById(Long id);

    List<Film> getModel();

    void clear();

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    List<Long> getAllLikes(Long filmId);
}
