package com.guideapp.guideapp.network;

import android.content.Context;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thales on 3/16/16.
 */
public class RestClient {
    private static GuideApi mGuideApi;
    private static String baseUrl = "https://guideapp-br.appspot.com/_ah/api/guideAppApi/v1/" ;

    private final static long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;
    private final static long CONNECTION_TIMEOUT = 5;

    public static GuideApi getClient(Context context) {
        if (mGuideApi == null) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getOkHttpClient(context))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mGuideApi = client.create(GuideApi.class);
        }

        return mGuideApi;
    }


    private static OkHttpClient getOkHttpClient(Context context) {
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BASIC);
        okClientBuilder.addInterceptor(httpLoggingInterceptor);
        File baseDir = context.getCacheDir();
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, "HttpResponseCache");
            okClientBuilder.cache(new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE));
        }
        okClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        okClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        okClientBuilder.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        return okClientBuilder.build();
    }
}
