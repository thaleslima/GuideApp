package com.guideapp.backend.util;

import java.util.List;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    /**
     * Check if String is null or empty
     */
    public static boolean nullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Check if Long is null or zero
     */
    public static boolean nullOrEmpty(Long s) {
        return s == null || s == 0;
    }

    /**
     * Check if double is zero
     */
    public static boolean nullOrEmpty(double s) {
        return s == 0;
    }

    /**
     * Check if List is null or empty
     */
    public static boolean nullOrEmpty(List<Long> s) {
        return s == null || s.isEmpty();
    }
}
