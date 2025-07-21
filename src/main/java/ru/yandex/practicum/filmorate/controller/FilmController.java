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
        if (film.getName() != null) {
            log.debug("film with id={}, had name= {} now it is {}", film.getId(), model.get(film.getId()).getName(), film.getName());
            model.get(film.getId()).setName(film.getName());
        }
        if (film.getDescription() != null) {
            log.debug("film with id={}, had description= {} now it is {}", film.getId(), model.get(film.getId()).getDescription(), film.getDescription());
            model.get(film.getId()).setDescription(film.getDescription());
        }
        if (film.getDuration() != null) {
            log.debug("film with id={}, had duration= {} now it is {}", film.getId(), model.get(film.getId()).getDuration(), film.getDuration());
            model.get(film.getId()).setDuration(film.getDuration());
        }
        if (film.getReleaseDate() != null) {
            log.debug("film with id={}, had ReleaseDate= {} now it is {}", film.getId(), model.get(film.getId()).getReleaseDate(), film.getReleaseDate());
            model.get(film.getId()).setReleaseDate(film.getReleaseDate());
        }
    }

    @Override
    public Long getId(Film film) {
        return film.getId();
    }

    @Override
    public void putObj(Film object) {
    }

}
