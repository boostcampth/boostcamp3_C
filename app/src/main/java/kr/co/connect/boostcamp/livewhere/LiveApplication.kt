package kr.co.connect.boostcamp.livewhere

import android.app.Application
import kr.co.connect.boostcamp.livewhere.di.appModules
import org.koin.android.ext.android.startKoin

class LiveApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, appModules)
    }
}