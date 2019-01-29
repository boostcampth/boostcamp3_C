package kr.co.connect.boostcamp.livewhere.di

import io.reactivex.schedulers.Schedulers
import kr.co.connect.boostcamp.livewhere.api.Api
import kr.co.connect.boostcamp.livewhere.util.localhost
import okhttp3.OkHttpClient
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
                    .build()
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(localhost)
            .build()
            .create(Api::class.java)
    }
}