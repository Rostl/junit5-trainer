package com.rost.integration.dao;

import com.rost.entity.Gender;
import com.rost.entity.Role;
import com.rost.entity.User;
import com.rost.integration.IntegrationTestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDaoIntegrationTest extends IntegrationTestBase {

    private UserDao userDao;

    private static final Integer DEFAULT_USER_ID = 1;

    @BeforeAll
    void before() {
        userDao = UserDao.getInstance();
    }

    @Test
    void checkFOrFindAll() {
        List<User> allUsers = userDao.findAll();

        assertThat(allUsers).hasSize(5);
    }

    @Test
    void checkForFindById() {
        Integer invalidUserId = 777;

        assertAll(
                () -> assertThat(userDao.findById(DEFAULT_USER_ID))
                        .isPresent(),
                () -> assertThat(userDao.findById(invalidUserId))
                        .isNotPresent()
        );
    }

    @Test
    void checkForCorrectSaving() {
        User actualUser = buildUserEntity();

        userDao.save(actualUser);
        Optional<User> expectedUser = userDao.findById(actualUser.getId());

        assertThat(expectedUser).isPresent();
        assertThat(expectedUser.get()).isEqualTo(actualUser);
    }

    @Test
    void checkForFindByEmailAndPassword() {
        assertAll(
                () -> assertThat(userDao.findByEmailAndPassword("ivan@gmail.com", "111"))
                        .isPresent(),
                () -> assertThat(userDao.findByEmailAndPassword("invalidEmail", "111"))
                        .isEmpty(),
                () -> assertThat(userDao.findByEmailAndPassword("ivan@gmail.com", "invalidPassword"))
                        .isEmpty()
        );
    }


    @Test
    void delete() {
        userDao.delete(DEFAULT_USER_ID);
        Optional<User> actualUser = userDao.findById(DEFAULT_USER_ID);

        assertThat(actualUser).isNotPresent();
    }

    @Test
    void update() {
        Optional<User> user = userDao.findById(DEFAULT_USER_ID);
        assertThat(user).isPresent();

        user.get().setPassword("somePassword");
        userDao.update(user.get());
        Optional<User> actualUser = userDao.findById(DEFAULT_USER_ID);

        assertThat(actualUser).isPresent();
        assertThat(actualUser.get().getPassword()).isEqualTo("somePassword");
    }

    private User buildUserEntity() {
        return User.builder()
                .name("Ivan")
                .password("password")
                .email("user@mail.ru")
                .gender(Gender.MALE)
                .birthday(LocalDate.of(1986, 4, 12))
                .role(Role.USER)
                .build();
    }
}