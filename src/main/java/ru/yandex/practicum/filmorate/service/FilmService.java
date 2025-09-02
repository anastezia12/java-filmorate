package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    public static final Comparator<Film> POPULARITY_DESC =
            Comparator.comparingInt((Film f) -> f.getLikes() == null ? 0 : f.getLikes().size())
                    .reversed();
    @Getter
    private final FilmStorage filmStorage;
    @Getter
    private final UserService userService;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public void addLike(Long idOfFilm, Long idOfUser) {
        if (!filmStorage.containsFilmWithKey(idOfFilm)) {
            throw new IllegalArgumentException("There are no such film with id " + idOfFilm);
        }
        userService.userContainsInStorage(idOfUser);

        Set<Long> likes = filmStorage.getById(idOfFilm).getLikes();
        if (likes.contains(idOfUser)) {
            throw new IllegalArgumentException("User with id " + idOfUser + " already added like");
        }

        likes.add(idOfUser);
    }

    public void deleteLike(Long idOfFilm, Long idOfUser) {
        if (!filmStorage.containsFilmWithKey(idOfFilm)) {
            throw new IllegalArgumentException("There are no such film with id " + idOfFilm);
        }
        userService.userContainsInStorage(idOfUser);
        Set<Long> likes = filmStorage.getById(idOfFilm).getLikes();
        if (!likes.contains(idOfUser)) {
            throw new IllegalArgumentException("User with id " + idOfUser + " do not have a like here");
        }
        likes.remove(idOfUser);
    }

    public List<Film> mostPopular(Long count) {
        return filmStorage.getModel().stream()
                .sorted(POPULARITY_DESC)
                .limit(count)
                .collect(Collectors.toList());
    }

}
