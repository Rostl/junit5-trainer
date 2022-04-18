package com.rost.mapper;

import com.rost.dto.CreateUserDto;
import com.rost.entity.Gender;
import com.rost.entity.Role;
import com.rost.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.rost.builder.DefaultEntityBuilder.*;
import static org.assertj.core.api.Assertions.*;

class CreateUserMapperTest {

    @Test
    void checkForAllFieldsCorrectMapping() {
        CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
        CreateUserDto createUserDto = buildCreateUserDto();
        User expectedUser = buildExpectedUserEntity();

        User actualUser = createUserMapper.map(createUserDto);
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    private User buildExpectedUserEntity() {
        return User.builder()
                .name("Ivan")
                .password("password")
                .email("test@mail.ru")
                .birthday(LocalDate.of(1986, 8, 28))
                .gender(Gender.MALE)
                .role(Role.ADMIN)
                .build();
    }
}