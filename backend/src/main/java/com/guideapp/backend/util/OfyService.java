package com.guideapp.backend.util;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.guideapp.backend.entity.Category;
import com.guideapp.backend.entity.City;
import com.guideapp.backend.entity.Local;
import com.guideapp.backend.entity.SubCategory;

public final class OfyService {
    private OfyService() {
    }

    static {
        ObjectifyService.register(SubCategory.class);
        ObjectifyService.register(Category.class);
        ObjectifyService.register(City.class);
        ObjectifyService.register(Local.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}