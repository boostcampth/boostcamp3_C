package kr.co.connect.boostcamp.livewhere

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.bumptech.glide.Glide
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.naver.maps.map.NaverMapSdk
import io.fabric.sdk.android.Fabric
import kr.co.connect.boostcamp.livewhere.di.appModules
import org.koin.android.ext.android.startKoin


// FIXME MultiDex 이슈가 발생할 여지가 충분하기때문에 gradle과 Application클래스에서 Multidex 환경을 구성해주세요
class LiveApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        //debug변수를 놓고 debug 인지 캐치함.
        if(BuildConfig.isDebug){
            Stetho.initializeWithDefaults(this)
        }
        startKoin(this, appModules)
        //Firebase Crashlytics
        Fabric.with(this, Crashlytics())
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NaverClientId)
    }

    //application class에서 MultiDex 사용
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
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