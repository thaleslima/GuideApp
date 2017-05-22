package com.guideapp.utilities

object Constants {
    const val ACTION_DATA_UPDATED = "com.guideapp.ACTION_DATA_UPDATED"
    const val ACTION_DATA_SYNC_ERROR = "com.guideapp.ACTION_DATA_SYNC_ERROR"

    interface City {
        companion object {
            const val ID = 5659118702428160L
            const val LATITUDE = -20.3449802
            const val LONGITUDE = -46.8551188
        }
    }

    internal interface Menu {
        companion object {
            const val ALIMENTATION = 5684666375864320L
            const val ATTRACTIVE = 5651124426113024L
            const val ACCOMMODATION = 5679413765079040L
        }
    }

    interface Analytics {
        companion object {
            const val SAVE_FAVORITE = "save_favorite"
            const val REMOVE_FAVORITE = "remove_favorite"
            const val SCREEN = "screen"
        }
    }
}
