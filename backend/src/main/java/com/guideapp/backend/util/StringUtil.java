package com.guideapp.backend.util;

/**
 * Created by thales on 2/20/16.
 */
public class StringUtil {

    public static String removeSpaceWhite(String s) {
        if(s == null)
            return "";
        return s.trim();
    }
}
