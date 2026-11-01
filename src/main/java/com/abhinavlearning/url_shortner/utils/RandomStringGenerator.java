package com.abhinavlearning.url_shortner.utils;

import java.security.SecureRandom;

public class RandomStringGenerator {

    private static final String ALPHABET =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generateSecureRandomString(int length) {
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = SECURE_RANDOM.nextInt(ALPHABET.length());
            result.append(ALPHABET.charAt(index));
        }

        return result.toString();
    }

}
