package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film addFilm(Film film);

    void deleteById(Long id);

    Film updateFilm(Film film);

    boolean containsFilmWithKey(Long id);

    Film getById(Long id);

    Collection<Film> getModel();
}
