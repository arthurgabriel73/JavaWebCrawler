package com.axreng.backend.util;

import java.util.Random;

public class IDGenerator {
    private static final String ALPHANUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int ID_LENGTH = 8;

    private IDGenerator() {}
    
    public static String generateID() {
        Random random = new Random();
        StringBuilder idBuilder = new StringBuilder(ID_LENGTH);
        while (idBuilder.length() < ID_LENGTH) {
            int randomIndex = random.nextInt(ALPHANUMERIC_CHARS.length());
            char randomChar = ALPHANUMERIC_CHARS.charAt(randomIndex);
            idBuilder.append(randomChar);
        }
        return idBuilder.toString();
    }

}
