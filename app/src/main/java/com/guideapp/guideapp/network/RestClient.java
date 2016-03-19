package com.guideapp.guideapp.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thales on 3/16/16.
 */
public class RestClient {
    private static GuideApi mGuideApi;
    private static String baseUrl = "https://guideapp-br.appspot.com/_ah/api/guideAppApi/v1/" ;

    public static GuideApi getClient() {
        if (mGuideApi == null) {

            OkHttpClient okClient = new OkHttpClient();



            Retrofit client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mGuideApi = client.create(GuideApi.class);
        }

        return mGuideApi;
    }
}
