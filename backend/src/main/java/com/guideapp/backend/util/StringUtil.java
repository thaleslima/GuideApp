package com.guideapp.backend.util;

public final class StringUtil {
    private StringUtil() {
    }

    public static String trim(String s) {
        if (s == null) {
            return "";
        }

        return s.trim();
    }
}
