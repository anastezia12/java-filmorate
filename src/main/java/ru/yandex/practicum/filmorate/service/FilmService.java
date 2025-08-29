package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(Long idOfFilm, Long idOfUser) {
        if (!filmStorage.containsFilmWithKey(idOfFilm)) {
            throw new IllegalArgumentException("There are no such film with id " + idOfFilm);
        }
        Set<Long> likes = filmStorage.getById(idOfFilm).getLikes();
        if (likes.contains(idOfUser)) {
            throw new IllegalArgumentException("User with id " + idOfUser + " already added like");
        }

        likes.add(idOfUser);
        filmStorage.getById(idOfFilm).setLikes(likes);
    }

    public void deleteLike(Long idOfFilm, Long idOfUser) {
        if (!filmStorage.containsFilmWithKey(idOfFilm)) {
            throw new IllegalArgumentException("There are no such film with id " + idOfFilm);
        }
        Set<Long> likes = filmStorage.getById(idOfFilm).getLikes();
        if (!likes.contains(idOfUser)) {
            throw new IllegalArgumentException("User with id " + idOfUser + " do not have a like here");
        }

        likes.remove(idOfUser);
        filmStorage.getById(idOfFilm).setLikes(likes);
    }

    public List<Film> mostPopular(Long count) {
        if (count == null) {
            count = 10L;
        }
        return filmStorage.getModel().stream().sorted(Comparator.comparingInt((Film f) -> f.getLikes().size()).reversed())
                .limit(count).collect(Collectors.toList());
    }
}
