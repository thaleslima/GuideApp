package com.guideapp.backend.util;

import java.util.List;

/**
 * Created by thales on 2/20/16.
 */
public class ValidationUtil {
    public static boolean nullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean nullOrEmpty(Long s) {
        return s == null || s == 0;
    }

    public static boolean nullOrEmpty(double s) {
        return s == 0;
    }

    public static boolean nullOrEmpty(List<Long> s) {
        return s == null || s.isEmpty();
    }
}
