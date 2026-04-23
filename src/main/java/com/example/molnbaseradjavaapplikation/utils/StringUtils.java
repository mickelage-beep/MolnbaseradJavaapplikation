package com.example.molnbaseradjavaapplikation.utils;

public class StringUtils {
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String title(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }

        String[] words = str.trim().split("\\s+");;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                sb.append(capitalize(words[i]));
            }

            if (i < words.length - 1) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }
}
