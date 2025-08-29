package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> model = new HashMap<>();

    @Override
    public User addUser(User user) {
        Long id = getNextId();
        user.setId(id);
        model.put(id, user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        model.remove(id);
    }

    @Override
    public User updateUser(User user) {
        User foundUser = model.get(user.getId());
        if (user.getEmail() != null) {
            foundUser.setEmail(user.getEmail());
        }
        if (user.getLogin() != null) {
            foundUser.setLogin(user.getLogin());
        }
        foundUser.setName(user.getName());
        if (user.getBirthday() != null) {
            foundUser.setBirthday(user.getBirthday());
        }
        return user;
    }

    private long getNextId() {
        long currentMaxId = model.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }

    @Override
    public Collection<User> getModel() {
        return model.values();
    }

    @Override
    public boolean containsUserWithKey(Long id) {
        return model.containsKey(id);
    }

    @Override
    public User getById(Long id) {
        return model.get(id);
    }
}
