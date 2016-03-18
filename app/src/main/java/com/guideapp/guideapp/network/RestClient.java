package com.guideapp.guideapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thales on 3/16/16.
 */
public class RestClient {
    private static GuideApi mGuideApi;
    private static String baseUrl = "https://guideapp-br.appspot.com/_ah/api/guideAppApi/v1/" ;

    public static GuideApi getClient() {
        if (mGuideApi == null) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mGuideApi = client.create(GuideApi.class);
        }

        return mGuideApi;
    }
}
