package com.guideapp.data.remote

import com.guideapp.BuildConfig

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {
    private var mGuideApi: GuideApi? = null
    private val TIMEOUT = 30

    val client: GuideApi
        get() {
            if (mGuideApi == null) {
                val client = Retrofit.Builder()
                        .baseUrl(BuildConfig.ENDPOINT_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                mGuideApi = client.create(GuideApi::class.java)
            }

            return mGuideApi!!
        }


    private val okHttpClient: OkHttpClient
        get() {
            val okClientBuilder = OkHttpClient.Builder()

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            okClientBuilder.addInterceptor(httpLoggingInterceptor)
            okClientBuilder.connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            okClientBuilder.readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            okClientBuilder.writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            return okClientBuilder.build()
        }
}
