package com.abhinavlearning.url_shortner.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

class HashGeneratorTest {

    @Test
    void generatesDeterministicSixCharacterBase62Code() {
        String first = HashGenerator.sha256("https://example.com");
        String second = HashGenerator.sha256("https://example.com");

        assertEquals(first, second);
        assertEquals(6, first.length());
        assertTrue(first.chars().allMatch(character ->
                Character.isDigit(character)
                        || character >= 'a' && character <= 'z'
                        || character >= 'A' && character <= 'Z'));
    }

    @Test
    void generatesDifferentCodeForDifferentInput() {
        assertNotEquals(HashGenerator.sha256("first"), HashGenerator.sha256("second"));
    }

    @Test
    void convertsZeroToBase62Zero() {
        assertEquals("0", HashGenerator.toBase62(BigInteger.ZERO));
    }

    @Test
    void reportsUnavailableDigestAlgorithm() {
        assertThrows(IllegalStateException.class,
                () -> HashGenerator.sha256("value", "missing-algorithm"));
    }

    @Test
    void utilityClassCanBeConstructedOnlyThroughReflectionForCoverage() throws Exception {
        var constructor = HashGenerator.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertEquals(HashGenerator.class, constructor.newInstance().getClass());
    }
}
