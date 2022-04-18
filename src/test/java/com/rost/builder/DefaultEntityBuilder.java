package com.rost.builder;

import com.rost.dto.CreateUserDto;
import com.rost.dto.UserDto;
import com.rost.entity.Gender;
import com.rost.entity.Role;
import com.rost.entity.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class DefaultEntityBuilder {

    public static final int DEFAULT_USER_ID = 4;

    public static UserDto buildUserDto() {
        return UserDto.builder()
                .id(DEFAULT_USER_ID)
                .name("Ivan")
                .birthday(LocalDate.of(1986, 8, 28))
                .email("test@mail.ru")
                .gender(Gender.MALE)
                .role(Role.ADMIN)
                .build();
    }

    public static User buildUserEntity() {
        return User.builder()
                .id(DEFAULT_USER_ID)
                .name("Anna")
                .password("password")
                .birthday(LocalDate.of(1993, 4, 12))
                .email("anna@mail.ru")
                .gender(Gender.FEMALE)
                .role(Role.USER)
                .build();
    }

    public static CreateUserDto buildCreateUserDto() {
        return CreateUserDto.builder()
                .name("Ivan")
                .password("password")
                .email("test@mail.ru")
                .gender("MALE")
                .birthday("1986-08-28")
                .role("ADMIN")
                .build();
    }

    public static UserDto buildIvanDto() {
        return UserDto.builder()
                .name("Ivan")
                .birthday(LocalDate.of(1990, 1, 10))
                .email("ivan@gmail.com")
                .gender(Gender.MALE)
                .role(Role.ADMIN)
                .build();
    }
}
