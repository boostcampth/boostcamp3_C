package kr.co.connect.boostcamp.livewhere.di

import kr.co.connect.boostcamp.livewhere.data.SharedPreferenceStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val sharedModule = module {

    single {
        SharedPreferenceStorage(androidContext())
    }
}