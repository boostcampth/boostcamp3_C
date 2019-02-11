package kr.co.connect.boostcamp.livewhere.di

import androidx.room.Room
import kr.co.connect.boostcamp.livewhere.data.database.AppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val recentSearchDatabaseModule = module {
    single("recentSearchDatabaseModle") {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "recent_search_database")
            .build().recentSearchDao()
    }
}