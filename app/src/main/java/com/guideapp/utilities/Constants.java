package com.guideapp.utilities;

public final class Constants {
    public static final String ACTION_DATA_UPDATED = "com.guideapp.ACTION_DATA_UPDATED";
    public static final String ACTION_DATA_SYNC_ERROR = "com.guideapp.ACTION_DATA_SYNC_ERROR";

    private Constants() {
    }

    public interface City {
        long ID = 5659118702428160L;
        double LATITUDE = -20.3449802;
        double LONGITUDE = -46.8551188;
    }

    interface Menu {
        long ALIMENTATION = 5684666375864320L;
        long ATTRACTIVE = 5651124426113024L;
        long ACCOMMODATION  = 5679413765079040L;
    }

    public interface Analytics {
        String SAVE_FAVORITE = "save_favorite";
        String REMOVE_FAVORITE = "remove_favorite";
        String SCREEN = "screen";
    }
}
