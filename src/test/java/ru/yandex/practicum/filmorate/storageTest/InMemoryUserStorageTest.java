package ru.yandex.practicum.filmorate.storageTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryUserStorageTest {
    private static User user;
    private InMemoryUserStorage inMemoryUserStorage;

    @BeforeAll
    public static void setUpUser() {
        user = new User("new@email.com", "login", "name", LocalDate.now().minusDays(10));
    }

    @BeforeEach
    public void setUpStorage() {
        inMemoryUserStorage = new InMemoryUserStorage();
    }

    @Test
    public void canAddUser() {
        inMemoryUserStorage.addUser(user);
        assertEquals(1, user.getId());
    }

    @Test
    public void canDeleteById() {
        inMemoryUserStorage.addUser(user);
        inMemoryUserStorage.deleteById(1L);
        assertEquals(0, inMemoryUserStorage.getModel().size());
    }

    @Test
    public void canUpdateUser() {
        User newUser = new User("new@email.com", "New User", null, null);
        inMemoryUserStorage.addUser(user);
        newUser.setId(1L);
        inMemoryUserStorage.updateUser(newUser);
        assertEquals(newUser.getLogin(), inMemoryUserStorage.getById(1L).getLogin());
    }

    @Test
    public void willNotUpdateNullFields() {
        User newUser = new User("new@email.com", "New User", null, null);
        inMemoryUserStorage.addUser(user);
        newUser.setId(1L);
        inMemoryUserStorage.updateUser(newUser);
        assertNotNull(inMemoryUserStorage.getById(1L).getName());
    }

    @Test
    public void canGetById() {
        inMemoryUserStorage.addUser(user);
        assertEquals(user, inMemoryUserStorage.getById(1L));
    }

    @Test
    public void containsUserWithKeyTrue() {
        inMemoryUserStorage.addUser(user);
        assertTrue(inMemoryUserStorage.containsUserWithKey(user.getId()));
    }

    @Test
    public void containsUserWithKeyFalse() {
        assertFalse(inMemoryUserStorage.containsUserWithKey(user.getId()));
    }

    @Test
    public void willReturnEmptyModelIfNoUsers() {
        assertEquals(0, inMemoryUserStorage.getModel().size());
    }

    @Test
    public void willReturnModelWithUsers() {
        inMemoryUserStorage.addUser(user);
        inMemoryUserStorage.addUser(user);
        assertEquals(2, inMemoryUserStorage.getModel().size());
    }
}
