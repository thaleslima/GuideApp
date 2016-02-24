package com.guideapp.backend.util;

/**
 * Created by thales on 2/20/16.
 */
public final class StringUtil {

    /**
     * Constructor
     */
    private StringUtil() {
    }

    /**
     * Remove white space in a String
     *
     * @param s the String
     * @return a String without white space
     */
    public static String trim(String s) {
        if (s == null) {
            return "";
        }

        return s.trim();
    }
}
