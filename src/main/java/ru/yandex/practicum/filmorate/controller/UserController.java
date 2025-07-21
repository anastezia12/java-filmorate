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
            log.debug("User with id={}, had email= {} now it is {}", user.getId(), foundUser.getEmail(), user.getEmail());
            foundUser.setEmail(user.getEmail());
        }
        if (user.getLogin() != null) {
            log.debug("User with id={}, had login= {} now it is {}", user.getId(), foundUser.getLogin(), user.getLogin());
            foundUser.setLogin(user.getLogin());
        }
        foundUser.setName(user.getName());
        if (user.getBirthday() != null) {
            log.debug("User with id={}, had Birthday= {} now it is {}", user.getId(), foundUser.getBirthday(), user.getBirthday());
            foundUser.setBirthday(user.getBirthday());
        }
    }

    @Override
    public Long getId(User user) {
        return user.getId();
    }


}
