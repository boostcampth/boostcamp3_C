package kr.co.connect.boostcamp.livewhere

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.facebook.stetho.Stetho
import com.naver.maps.map.NaverMapSdk
import kr.co.connect.boostcamp.livewhere.BuildConfig.NaverClientId
import kr.co.connect.boostcamp.livewhere.di.appModules
import org.koin.android.ext.android.startKoin

class LiveApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin(applicationContext, appModules)
        //Firebase Crashlytics
        //Fabric.with(this, Crashlytics())
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(NaverClientId)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(this).trimMemory(level)
    }
}