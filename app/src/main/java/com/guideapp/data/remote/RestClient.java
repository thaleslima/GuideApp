package com.guideapp.data.remote;

import com.guideapp.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static GuideApi mGuideApi;

    public static GuideApi getClient() {
        if (mGuideApi == null) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(BuildConfig.ENDPOINT_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mGuideApi = client.create(GuideApi.class);
        }

        return mGuideApi;
    }


    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        okClientBuilder.addInterceptor(httpLoggingInterceptor);
        return okClientBuilder.build();
    }
}
