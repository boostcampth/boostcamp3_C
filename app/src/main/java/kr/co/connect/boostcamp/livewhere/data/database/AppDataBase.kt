package kr.co.connect.boostcamp.livewhere.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.co.connect.boostcamp.livewhere.data.dao.BookmarkDAO
import kr.co.connect.boostcamp.livewhere.data.dao.RecentSearchDAO
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
import kr.co.connect.boostcamp.livewhere.data.entity.RecentSearchEntity

@Database(entities = [BookmarkEntity::class, RecentSearchEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDAO
    abstract fun recentSearchDao(): RecentSearchDAO
}