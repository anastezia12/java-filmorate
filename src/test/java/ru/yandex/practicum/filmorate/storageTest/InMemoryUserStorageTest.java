package ru.yandex.practicum.filmorate.storageTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class InMemoryUserStorageTest {
    private InMemoryUserStorage inMemoryUserStorage;
    private User user = new User("new@email.com", "login", "name", LocalDate.now().minusDays(10));

    @Autowired
    public InMemoryUserStorageTest(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    @Test
    @Order(3)
    public void canAddUser() {
        int size = inMemoryUserStorage.getModel().size();
        inMemoryUserStorage.addUser(user);
        assertEquals(++size, inMemoryUserStorage.getModel().size());
    }

    @Test
    @Order(4)
    public void canDeleteById() {
        int size = inMemoryUserStorage.getModel().size();
        inMemoryUserStorage.deleteById(1L);
        assertEquals(--size, inMemoryUserStorage.getModel().size());
    }

    @Test
    @Order(5)
    public void canUpdateUser() {
        User newUser = new User("new@email.com", "New User", null, null);
        inMemoryUserStorage.addUser(user);
        newUser.setId(user.getId());
        inMemoryUserStorage.updateUser(newUser);
        assertEquals(newUser.getLogin(), inMemoryUserStorage.getById(user.getId()).getLogin());
    }

    @Test
    @Order(6)
    public void willNotUpdateNullFields() {
        User newUser = new User("new@email.com", "New User", null, null);
        inMemoryUserStorage.addUser(user);
        newUser.setId(user.getId());
        inMemoryUserStorage.updateUser(newUser);
        assertNotNull(inMemoryUserStorage.getById(user.getId()).getName());
    }

    @Test
    @Order(7)
    public void canGetById() {
        inMemoryUserStorage.addUser(user);
        assertEquals(user, inMemoryUserStorage.getById(user.getId()));
    }

    @Test
    @Order(8)
    public void containsUserWithKeyTrue() {
        inMemoryUserStorage.addUser(user);
        assertTrue(inMemoryUserStorage.containsUserWithKey(user.getId()));
    }

    @Test
    @Order(9)
    public void containsUserWithKeyFalse() {
        assertFalse(inMemoryUserStorage.containsUserWithKey(user.getId()));
    }

    @Test
    @Order(1)
    public void willReturnEmptyModelIfNoUsers() {
        assertEquals(0, inMemoryUserStorage.getModel().size());
    }

    @Test
    @Order(2)
    public void willReturnModelWithUsers() {
        inMemoryUserStorage.addUser(user);
        inMemoryUserStorage.addUser(user);
        assertEquals(2, inMemoryUserStorage.getModel().size());
    }
}
