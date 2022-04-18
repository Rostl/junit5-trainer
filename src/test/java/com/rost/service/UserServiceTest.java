package com.rost.service;

import com.rost.integration.dao.UserDao;
import com.rost.dto.CreateUserDto;
import com.rost.dto.UserDto;
import com.rost.entity.User;
import com.rost.exception.ValidationException;
import com.rost.mapper.CreateUserMapper;
import com.rost.mapper.UserMapper;
import com.rost.validator.CreateUserValidator;
import com.rost.validator.Error;
import com.rost.validator.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.rost.builder.DefaultEntityBuilder.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith({MockitoExtension.class})
class UserServiceTest {

    @Mock
    private CreateUserValidator createUserValidator;
    @Mock
    private UserDao userDao;
    @Mock
    private CreateUserMapper createUserMapper;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void checkForUserLogin() {
        UserDto userDto = buildUserDto();
        User user = User.builder().build();

        doReturn(Optional.of(user))
                .when(userDao).findByEmailAndPassword(anyString(), anyString());
        doReturn(userDto)
                .when(userMapper).map(any());
        Optional<UserDto> maybeUser = userService.login(anyString(), anyString());

        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.get().getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    void checkForUserSavingAndMappAllFields() {
        UserDto userDto = buildUserDto();
        CreateUserDto createUserDto = buildCreateUserDto();
        User userEntity = User.builder().build();

        doReturn(new ValidationResult())
                .when(createUserValidator).validate(any(CreateUserDto.class));
        doReturn(userEntity)
                .when(createUserMapper).map(createUserDto);
        doReturn(userDto)
                .when(userMapper).map(userEntity);

        UserDto createUser = userService.create(createUserDto);

        verify(userDao).save(userEntity);
        assertThat(createUser).isNotNull();
        assertThat(createUser.getEmail()).isEqualTo(createUserDto.getEmail());

    }

    @Test
    void checkForValidationExceptionWhileCreateUser() {
        CreateUserDto createUserDto = buildCreateUserDto();
        ValidationResult validationResult = new ValidationResult();
        validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));

        doReturn(validationResult)
                .when(createUserValidator).validate(createUserDto);
        ValidationException actualException = assertThrows(ValidationException.class,
                () -> userService.create(createUserDto));

        verify(userDao, never()).save(any());
        assertThat(actualException.getErrors().get(0).getMessage()).
                isEqualTo(validationResult.getErrors().get(0).getMessage());
    }
}