package ru.yandex.practicum.filmorate.storage.inMemory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
@Qualifier("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> model = new HashMap<>();

    @Override
    public Film addFilm(Film film) {
        Long id = getNextId();
        film.setId(id);
        model.put(id, film);
        return film;
    }

    @Override
    public void deleteById(Long id) {
        model.remove(id);
    }

    @Override
    public Film getById(Long id) {
        return model.get(id);
    }

    @Override
    public Film updateFilm(Film film) {
        if (!model.containsKey(film.getId())) {
            throw new NoSuchElementException("there are no such user with id " + film.getId());
        }
        Film existingFilm = model.get(film.getId());
        existingFilm.setName(film.getName());
        if (film.getDescription() != null) {
            existingFilm.setDescription(film.getDescription());
        }
        if (film.getDuration() != null) {
            existingFilm.setDuration(film.getDuration());
        }
        if (film.getReleaseDate() != null) {
            existingFilm.setReleaseDate(film.getReleaseDate());
        }
        return existingFilm;
    }

    private long getNextId() {
        long currentMaxId = model.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    @Override
    public List<Film> getModel() {
        return model.values().stream().toList();
    }

    @Override
    public boolean containsFilmWithKey(Long id) {
        return model.containsKey(id);
    }

    public void clear() {
        model.clear();
    }

    @Override
    public void addLike(Long filmId, Long userId) {

    }

    @Override
    public void deleteLike(Long filmId, Long userId) {

    }

    @Override
    public List<Long> getAllLikes(Long filmId) {
        return List.of();
    }
}
