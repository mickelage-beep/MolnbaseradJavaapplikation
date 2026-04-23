package com.example.molnbaseradjavaapplikation;


import com.example.molnbaseradjavaapplikation.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsTests {
    @ParameterizedTest
    @CsvSource({
            "kimmo,     Kimmo",
            "ahola,     Ahola",
            "AHOLA,     Ahola",
            "KIMMO AHOLA, Kimmo ahola",
            "123, 123",
            "åäö, Åäö"
    })
    void testCapitalize(String input, String expected) {
        assertEquals(expected, StringUtils.capitalize(input));
    }

    @Test
    void testCapitalizeWithNull() {
        // Arrange
        String input = null;

        // Act
        String result = StringUtils.capitalize(input);

        // Assert
        assertEquals("",  result);
    }

    @Test
    void testCapitalizeWithEmpty() {
        // Arrange
        String input = "";

        // Act
        String result = StringUtils.capitalize(input);

        // Assert
        assertEquals("",  result);
    }


    @ParameterizedTest
    @CsvSource({
            "kimmo ahola,     Kimmo Ahola",
            "AHOLA,     Ahola",
            "KIMMO AHOLA, Kimmo Ahola",
            "123, 123",
            "åäö, Åäö"
    })
    void testTitle(String input, String expected) {
        assertEquals(expected, StringUtils.title(input));
    }

    @Test
    void testTitleWithNull() {
        // Arrange
        String input = null;

        // Act
        String result = StringUtils.title(input);

        // Assert
        assertEquals("",  result);
    }

    @Test
    void testTitleWithEmpty() {
        // Arrange
        String input = "";

        // Act
        String result = StringUtils.title(input);

        // Assert
        assertEquals("",  result);
    }
}
