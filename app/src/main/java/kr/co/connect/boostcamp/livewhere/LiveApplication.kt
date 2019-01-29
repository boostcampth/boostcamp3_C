package kr.co.connect.boostcamp.livewhere

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import kr.co.connect.boostcamp.livewhere.di.appModules
import org.koin.android.ext.android.startKoin

class LiveApplication : Application(){

    lateinit var glide : Glide

    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, appModules)
        glide = Glide.get(this)
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