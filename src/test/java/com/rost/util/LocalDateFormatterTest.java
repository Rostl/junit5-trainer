package com.rost.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class LocalDateFormatterTest {

    private String validDate;
    private String invalidDate;

    @BeforeEach
    void before() {
        validDate = "1986-05-13";
        invalidDate = "1986.05.13";
    }

    @Test
    void checkForCorrectLocalDate() {
        validDate = "1986-05-13";

        LocalDate actual = LocalDateFormatter.format(validDate);

        assertThat(actual).isEqualTo(validDate);
    }

    @Test
    void checkForInvalidDateFormat() {
        assertThrows(DateTimeException.class, () -> LocalDateFormatter.format(invalidDate));
    }

    @Test
    void checkForIsValidCorrectly() {
        boolean isValid = LocalDateFormatter.isValid(validDate);

        assertThat(isValid).isTrue();
    }

    @Test
    void checkForDateIsNotValid() {
        boolean isValid = LocalDateFormatter.isValid(invalidDate);

        assertThat(isValid).isFalse();
    }

    @Test
    void checkForDateIsNotPresent(){
        boolean isValid = LocalDateFormatter.isValid(null);

        assertThat(isValid).isFalse();
    }
}