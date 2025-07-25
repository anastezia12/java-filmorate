package ru.yandex.practicum.filmorate.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

@RestController
@RequestMapping("/films")
public class FilmController extends AbstractController<Film> {
    public FilmController() {
        super(LoggerFactory.getLogger(FilmController.class));
    }

    @Override
    public void setId(Long id, Film film) {
        film.setId(id);
    }


    @Override
    public void updateFields(Film film) {
        Film existingFilm = model.get(film.getId());
        log.debug("film with id={}, had name= {} now it is {}", film.getId(), existingFilm.getName(), film.getName());
        existingFilm.setName(film.getName());
        if (film.getDescription() != null) {
            log.debug("film with id={}, had description= {} now it is {}", film.getId(), existingFilm.getDescription(), film.getDescription());
            existingFilm.setDescription(film.getDescription());
        }
        if (film.getDuration() != null) {
            log.debug("film with id={}, had duration= {} now it is {}", film.getId(), existingFilm.getDuration(), film.getDuration());
            existingFilm.setDuration(film.getDuration());
        }
        if (film.getReleaseDate() != null) {
            log.debug("film with id={}, had ReleaseDate= {} now it is {}", film.getId(), existingFilm.getReleaseDate(), film.getReleaseDate());
            existingFilm.setReleaseDate(film.getReleaseDate());
        }
    }

    @Override
    public Long getId(Film film) {
        return film.getId();
    }


}
