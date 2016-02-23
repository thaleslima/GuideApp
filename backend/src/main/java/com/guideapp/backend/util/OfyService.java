package com.guideapp.backend.util;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.guideapp.backend.entity.Category;
import com.guideapp.backend.entity.City;
import com.guideapp.backend.entity.Local;
import com.guideapp.backend.entity.SubCategory;

/**
 * Created by thales on 1/24/16.
 */
public final class OfyService {

    /**
     * Constructor
     */
    private OfyService() {
    }

    static {
        ObjectifyService.register(SubCategory.class);
        ObjectifyService.register(Category.class);
        ObjectifyService.register(City.class);
        ObjectifyService.register(Local.class);
    }

    /**
     * Return a Objectify object
     * @return a Objectify object
     */
    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    /**
     * Return a ObjectifyFactory object
     * @return a ObjectifyFactory object
     */
    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}