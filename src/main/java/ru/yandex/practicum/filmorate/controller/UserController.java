package ru.yandex.practicum.filmorate.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<User> {
    public UserController() {
        super(LoggerFactory.getLogger(UserController.class));
    }

    @Override
    public void setId(Long id, User user) {
        user.setId(id);
    }

    @Override
    public void updateFields(User user) {
        User foundUser = model.get(user.getId());
        if (user.getEmail() != null) {
            log.debug("User with id={}, had email= {} now it is {}", user.getId(), model.get(user.getId()).getEmail(), user.getEmail());
            foundUser.setEmail(user.getEmail());
        }
        nameDecider(user);
        foundUser.setName(user.getName());
        if (user.getLogin() != null) {
            log.debug("User with id={}, had login= {} now it is {}", user.getId(), model.get(user.getId()).getLogin(), user.getLogin());
            foundUser.setLogin(user.getLogin());
        }
        if (user.getBirthday() != null) {
            log.debug("User with id={}, had Birthday= {} now it is {}", user.getId(), model.get(user.getId()).getBirthday(), user.getBirthday());
            foundUser.setBirthday(user.getBirthday());
        }
    }

    @Override
    public Long getId(User user) {
        return user.getId();
    }

    @Override
    public void putObj(User user) {
        nameDecider(user);
    }


    private void nameDecider(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }

}
