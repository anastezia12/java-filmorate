package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.MpaRepository;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

@Service
public class MpaService {
    private final MpaRepository mpaRepository;

    @Autowired
    public MpaService(MpaRepository mpaRepository) {
        this.mpaRepository = mpaRepository;
    }

    public List<MPA> getAll() {
        return mpaRepository.getAllMpa();
    }

    public MPA findById(Long id) {
        return mpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no such mpa with id = " + id));

    }

}
