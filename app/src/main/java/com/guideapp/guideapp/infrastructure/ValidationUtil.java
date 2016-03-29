package com.guideapp.guideapp.infrastructure;

import java.util.List;

/**
 * Created by thales on 3/19/16.
 */
public final class ValidationUtil {
    /**
     * Constructor
     */
    private ValidationUtil() {
    }

    /**
     * Check if String is null or empty
     * @param s a string
     * @return return true if String is null or empty
     */
    public static boolean nullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Check if Long is null or zero
     * @param s a string
     * @return return true if String is null or zero
     */
    public static boolean nullOrEmpty(Long s) {
        return s == null || s == 0;
    }

    /**
     * Check if double is zero
     * @param s a string
     * @return return true if String is zero
     */
    public static boolean nullOrEmpty(double s) {
        return s == 0;
    }

    /**
     * Check if List is null or empty
     * @param s a string
     * @return return true if String is null or empty
     */
    public static boolean nullOrEmpty(List<Long> s) {
        return s == null || s.isEmpty();
    }
}
