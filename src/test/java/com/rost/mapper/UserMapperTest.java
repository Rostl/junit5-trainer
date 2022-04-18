package com.rost.mapper;

import com.rost.builder.DefaultEntityBuilder;
import com.rost.dto.UserDto;
import com.rost.entity.Gender;
import com.rost.entity.Role;
import com.rost.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class UserMapperTest {

    @Test
    void checkCorrectFieldsMapping() {
        UserMapper userMapper = UserMapper.getInstance();
        User user = DefaultEntityBuilder.buildUserEntity();
        UserDto expectedUser = builsExpectedUser();

        UserDto actualUser = userMapper.map(user);
        Assertions.assertThat(actualUser).isEqualTo(expectedUser);

    }

    private UserDto builsExpectedUser() {
        return UserDto.builder()
                .id(4)
                .name("Anna")
                .birthday(LocalDate.of(1993, 4, 12))
                .email("anna@mail.ru")
                .gender(Gender.FEMALE)
                .role(Role.USER)
                .build();
    }
}