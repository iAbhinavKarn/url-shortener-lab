package com.abhinavlearning.url_shortner.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RandomStringGeneratorTest {

    @Test
    void generatesRequestedLengthUsingBase62Characters() {
        String value = RandomStringGenerator.generateSecureRandomString(32);

        assertEquals(32, value.length());
        assertTrue(value.chars().allMatch(character ->
                Character.isDigit(character)
                        || character >= 'a' && character <= 'z'
                        || character >= 'A' && character <= 'Z'));
    }

    @Test
    void generatesEmptyStringForZeroLength() {
        assertEquals("", RandomStringGenerator.generateSecureRandomString(0));
    }

    @Test
    void utilityClassCanBeConstructedOnlyThroughReflectionForCoverage() throws Exception {
        var constructor = RandomStringGenerator.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertEquals(RandomStringGenerator.class, constructor.newInstance().getClass());
    }
}
