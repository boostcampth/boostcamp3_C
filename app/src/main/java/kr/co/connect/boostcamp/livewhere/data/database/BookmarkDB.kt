package kr.co.connect.boostcamp.livewhere.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kr.co.connect.boostcamp.livewhere.data.dao.BookmarkDAO
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 1)
abstract class BookmarkDB: RoomDatabase() {
    abstract fun BookmarkDAO(): BookmarkDAO

    companion object {
        private var INSTANCE: BookmarkDB? = null

        fun getInstance(context: Context): BookmarkDB? {
            if(INSTANCE == null) {
                synchronized(BookmarkDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        BookmarkDB::class.java, "bookmark.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destoryInstance() {
            INSTANCE = null
        }
    }
}