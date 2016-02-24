package com.guideapp.backend.util;

/**
 * Created by thales on 2/20/16.
 */
public final class Constants {
    /**
     * WEB_CLIENT_ID
     */
    public static final String WEB_CLIENT_ID
            = "904382967622-sl60lsln4f5fmnqac53n2medldl6nt40.apps.googleusercontent.com";

    /**
     * ANDROID_CLIENT_ID
     */
    public static final String ANDROID_CLIENT_ID = "2-android-apps.googleusercontent.com";

    /**
     * IOS_CLIENT_ID
     */
    public static final String IOS_CLIENT_ID = "3-ios-apps.googleusercontent.com";

    /**
     * ANDROID_AUDIENCE
     */
    public static final String ANDROID_AUDIENCE = WEB_CLIENT_ID;

    /**
     * EMAIL_SCOPE
     */
    public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";

    /**
     * AUTHORIZATION_REQUIRED
     */
    public static final String AUTHORIZATION_REQUIRED = "Authorization required";

    /**
     * Constructor
     */
    private Constants() {
    }
}
