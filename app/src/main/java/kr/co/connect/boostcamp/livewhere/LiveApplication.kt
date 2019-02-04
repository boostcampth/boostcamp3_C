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


// FIXME MultiDex 이슈가 발생할 여지가 충분하기때문에 gradle과 Application클래스에서 Multidex 환경을 구성해주세요
class LiveApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // FIXME 개발빌드에서만 stetho가 적용되도록 해주세요. 배포버전에서도 적용되면 사용자가 직접 앱을 디버깅 할 수 있는 문제가 있습니다.
        Stetho.initializeWithDefaults(this)
        startKoin(applicationContext, appModules)
        //Firebase Crashlytics
        Fabric.with(this, Crashlytics())
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(NaverClientId)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
        //GlideApp.get(this).clearMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(this).trimMemory(level)
        //GlideApp.get(this).trimMemory(level)
    }
}