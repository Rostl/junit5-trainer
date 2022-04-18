package com.rost.integration.service;

import com.rost.integration.dao.UserDao;
import com.rost.dto.UserDto;
import com.rost.integration.IntegrationTestBase;
import com.rost.mapper.CreateUserMapper;
import com.rost.mapper.UserMapper;
import com.rost.service.UserService;
import com.rost.validator.CreateUserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.rost.builder.DefaultEntityBuilder.*;
import static org.assertj.core.api.Assertions.*;

public class ServiceIntegrationTest extends IntegrationTestBase {

    private UserService userService;

    @BeforeEach
    void before() {
        userService = new UserService(CreateUserValidator.getInstance(),
                UserDao.getInstance(),
                CreateUserMapper.getInstance(),
                UserMapper.getInstance());
    }

    @Test
    void checkForLoginCorrectly() {

        UserDto ivan = buildIvanDto();
        Optional<UserDto> maybeUser =
                userService.login("ivan@gmail.com", "111");

        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(ivan);
    }

    @Test
    void checkForLoginFailed() {
        Optional<UserDto> maybeUser =
                userService.login("van@gmail.com", "111");

        assertThat(maybeUser).isEmpty();
    }
}