package kr.co.connect.boostcamp.livewhere.di

import androidx.room.Room
import kr.co.connect.boostcamp.livewhere.data.database.AppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val bookmarkDatabaseModule = module {
    single("bookmarkDatabaseModule") {
         Room.databaseBuilder(androidContext(), AppDataBase::class.java, "bookmark_database")
            .build().bookmarkDao()
    }
}