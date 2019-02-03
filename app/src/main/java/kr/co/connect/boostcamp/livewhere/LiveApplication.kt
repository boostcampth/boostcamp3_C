package kr.co.connect.boostcamp.livewhere

import android.app.Application
import com.bumptech.glide.Glide
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.naver.maps.map.NaverMapSdk
import io.fabric.sdk.android.Fabric
import kr.co.connect.boostcamp.livewhere.BuildConfig.NaverClientId
import kr.co.connect.boostcamp.livewhere.di.appModules
import org.koin.android.ext.android.startKoin





class LiveApplication : Application(){

    lateinit var glide : Glide

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin(applicationContext, appModules)
        glide = Glide.get(this)
        //Firebase Crashlytics
        Fabric.with(this, Crashlytics())
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(NaverClientId)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        glide.clearMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        glide.trimMemory(level)
    }
}