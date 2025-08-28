package com.brunetto.encourtner.util;

import java.util.Random;

public class UrlUtil {
    private UrlUtil() {
        throw new IllegalStateException("Class de utilidade não deve ser instanciada.");
    }

    public static String generateRandomString(int strLength) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(strLength);

        for (int i = 0; i < strLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }

        return sb.toString();
    }
}
