package com.guideapp.data.remote;

import com.guideapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static GuideApi mGuideApi;
    private static final int TIMEOUT = 30;

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
        okClientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        okClientBuilder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        okClientBuilder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        return okClientBuilder.build();
    }
}
