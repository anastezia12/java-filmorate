package ru.yandex.practicum.filmorate.controllerTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidationTest {
    private static Validator validator;
    private static User user;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    @BeforeEach
    public void setUpFilm() {
        user = new User("new@email.com", "login", "name", LocalDate.now().minusDays(10));
    }

    @Test
    public void validUserShouldSuccesses() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
        assertEquals("name", user.getName());
    }

    @Test
    public void nullNameShouldSuccesses() {
        User userWithoutName = new User("new@email.com", "login", null, LocalDate.now().minusDays(10));

        Set<ConstraintViolation<User>> violations = validator.validate(userWithoutName);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void nullNameShouldBecameLogin() {
        User userWithoutName = new User("new@email.com", "login", null, LocalDate.now().minusDays(10));
        assertEquals("login", userWithoutName.getName());
    }

    @Test
    public void blankNameShouldBecameLogin() {
        User userWithoutName = new User("new@email.com", "login", "   ", LocalDate.now().minusDays(10));
        assertEquals("login", userWithoutName.getName());
    }

    @Test
    public void yesterdayBirthdaySuccesses() {
        user.setBirthday(LocalDate.now().minusDays(1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void loginWithSpecialCharactersSuccesses() {
        user.setLogin("a!@#$%^&*()_+|}{");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void nullEmailShouldFail() {
        user.setEmail(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void specialCharactersEmailShouldFail() {
        user.setEmail("name@gmail.");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void blankShouldFail() {
        user.setLogin("   ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void nullLoginShouldFail() {
        user.setLogin(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void loginWithSpacesShouldFail() {
        user.setEmail("null now");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void nullBirthdayShouldFail() {
        user.setBirthday(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void todayBirthdaySpacesShouldFail() {
        user.setBirthday(LocalDate.now());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void futureBirthdaySpacesShouldFail() {
        user.setBirthday(LocalDate.now().plusDays(1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

}
