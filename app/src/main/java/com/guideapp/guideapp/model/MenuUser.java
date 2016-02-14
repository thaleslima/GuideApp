package com.guideapp.guideapp.model;

import com.guideapp.guideapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thales on 2/10/16.
 */
public class MenuUser {
    private int description;
    private int icon;
    private int type;

    public static int TYPE_HEADER = 0;
    public static int TYPE_ITEM = 1;

    public static List<MenuUser> MENU_USER_VISITOR;
    public static List<MenuUser> MENU_USER;

    public MenuUser(int description, int icon, int type) {
        this.description = description;
        this.icon = icon;
        this.type = type;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static List<MenuUser> getMenuUserVisitor(){
        if(MENU_USER_VISITOR == null) {
            MENU_USER_VISITOR = new ArrayList<>();

            MENU_USER_VISITOR.add(new MenuUser(0, 0, TYPE_HEADER));
            MENU_USER_VISITOR.add(new MenuUser(R.string.action_about,
                    R.drawable.ic_info_outline_black_24dp, TYPE_ITEM));

        }
        return MENU_USER_VISITOR;
    }

    public static List<MenuUser> getMenuUser(){
        if(MENU_USER == null) {
            MENU_USER = new ArrayList<>();

            MENU_USER.add(new MenuUser(0, 0, TYPE_HEADER));
            MENU_USER.add(new MenuUser(R.string.action_my_reviews,
                    R.drawable.ic_star_black_24dp, TYPE_ITEM));
            MENU_USER.add(new MenuUser(R.string.action_about,
                    R.drawable.ic_info_outline_black_24dp, TYPE_ITEM));
            MENU_USER.add(new MenuUser(R.string.action_logout,
                    R.drawable.ic_power_settings_new_black_24dp, TYPE_ITEM));

        }
        return MENU_USER;
    }
}
