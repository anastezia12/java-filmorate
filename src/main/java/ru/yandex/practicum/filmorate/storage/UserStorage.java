package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User addUser(User user);

    void deleteById(Long id);

    User updateUser(User user);

    List<User> getModel();

    boolean containsUserWithKey(Long id);

    User getById(Long id);
}
