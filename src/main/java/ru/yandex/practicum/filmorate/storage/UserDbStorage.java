package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@Qualifier("userDbStorage")
public class UserDbStorage implements UserStorage {
    private final UserRepository userRepository;

    @Autowired
    public UserDbStorage(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        if (!containsUserWithKey(user.getId())) {
            throw new NoSuchElementException("there are no such user with id " + user.getId());
        }
        return userRepository.update(user);
    }

    @Override
    public List<User> getModel() {
        return userRepository.findAll();
    }

    @Override
    public boolean containsUserWithKey(Long id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void clear() {
        userRepository.clear();
    }
}
