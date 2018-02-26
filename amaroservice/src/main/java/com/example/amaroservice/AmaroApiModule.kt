package com.example.amaroservice

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.threeten.bp.Clock
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by gibranlyra on 26/02/18 for amarotest.
 */

internal const val BASE_URL: String = "http://www.mocky.io/v2/"

object AmaroApiModule {
    lateinit var retrofit: Retrofit private set

    fun setRetrofit(logLevel: LoggingInterceptor.Level = LoggingInterceptor.Level.FULL) {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = LoggingInterceptor(Clock.systemDefaultZone(), logLevel)
        builder.addInterceptor(loggingInterceptor)

        val okClient = builder.build()
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create(getGsonBuilder()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun getGsonBuilder(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create()
    }
}
