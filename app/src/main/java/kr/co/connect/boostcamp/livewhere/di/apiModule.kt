package kr.co.connect.boostcamp.livewhere.di

import io.reactivex.schedulers.Schedulers
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.api.Api
import kr.co.connect.boostcamp.livewhere.api.TmapApi
import kr.co.connect.boostcamp.livewhere.util.TMAP_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    single("api") {
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor())//http log 확인
                    .build()
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BaseServerURL)
            .build()
            .create(Api::class.java)
    }

    single("tmapApi") {
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor())
                    .build()
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(TMAP_BASE_URL)
            .build()
            .create(TmapApi::class.java)
    }
}