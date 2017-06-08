package com.guideapp.utilities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

import com.guideapp.R
import com.guideapp.model.Local
import com.guideapp.model.MainMenu

import java.util.ArrayList

object Utility {
    fun isNetworkAvailable(c: Context): Boolean {
        val cm = c.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun updateWidgets(context: Context) {
        val dataUpdatedIntent = Intent(Constants.ACTION_DATA_UPDATED).setPackage(context.packageName)
        context.sendBroadcast(dataUpdatedIntent)
    }

    fun getTextToShare(context: Context, local: Local): String {
        return context.getString(R.string.share_text,
                local.description,
                local.address,
                local.descriptionSubCategories,
                "http://maps.google.com/maps?saddr=" + local.latitude + "," + local.longitude)
    }


    fun getIdDescriptionCategory(id: Long): Int {
        if (id == Constants.Menu.ACCOMMODATION) {
            return R.string.menu_accommodation
        }

        if (id == Constants.Menu.ALIMENTATION) {
            return R.string.menu_alimentation
        }

        if (id == Constants.Menu.ATTRACTIVE) {
            return R.string.menu_attractive
        }

        return 0
    }

    fun getIdImageCategory(id: Long): Int {
        if (id == Constants.Menu.ACCOMMODATION) {
            return R.drawable.ic_place_hotel_48dp
        }

        if (id == Constants.Menu.ALIMENTATION) {
            return R.drawable.ic_place_dining_48dp
        }

        if (id == Constants.Menu.ATTRACTIVE) {
            return R.drawable.ic_place_terrain_48dp
        }

        return 0
    }

    val menus: List<MainMenu>
        get() {
            val menus = ArrayList<MainMenu>()
            menus.add(MainMenu(1, R.string.menu_local, R.drawable.ic_map_white_36dp, R.color.green_500))
            menus.add(MainMenu(Constants.Menu.ALIMENTATION, R.string.menu_alimentation, R.drawable.ic_local_dining_white_36dp, R.color.blue_500))
            menus.add(MainMenu(Constants.Menu.ATTRACTIVE, R.string.menu_attractive, R.drawable.ic_terrain_white_36dp, R.color.cyan_500))
            menus.add(MainMenu(Constants.Menu.ACCOMMODATION, R.string.menu_accommodation, R.drawable.ic_local_hotel_white_36dp, R.color.purple_500))
            return menus
        }
}
