package com.guideapp.backend.util;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.util.logging.Level;

/**
 * Created by thales on 3/25/16.
 */
public class MemcacheUtil {

    public static MemcacheService getMemcacheService(){
        MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
        syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));

        return syncCache;
    }
}
