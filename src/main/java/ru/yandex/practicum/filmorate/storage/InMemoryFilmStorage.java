package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
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
        long currentMaxId = model.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }

    @Override
    public Collection<Film> getModel() {
        return model.values();
    }

    @Override
    public boolean containsFilmWithKey(Long id) {
        return model.containsKey(id);
    }

}
