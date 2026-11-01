package com.abhinavlearning.url_shortner.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

    private static final String BASE62_ALPHABET =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = digest.digest(
                    input.getBytes(StandardCharsets.UTF_8)
            );

            String base62Encoded = toBase62(new BigInteger(1, hashBytes));

            return base62Encoded.substring(0, 6);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is unavailable", e);
        }
    }

    private static String toBase62(BigInteger number) {
        if (number.equals(BigInteger.ZERO)) {
            return "0";
        }

        StringBuilder result = new StringBuilder();

        BigInteger base = BigInteger.valueOf(62);

        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] quotientAndRemainder = number.divideAndRemainder(base);

            int remainder = quotientAndRemainder[1].intValue();
            result.append(BASE62_ALPHABET.charAt(remainder));

            number = quotientAndRemainder[0];
        }

        return result.reverse().toString();
    }

}
