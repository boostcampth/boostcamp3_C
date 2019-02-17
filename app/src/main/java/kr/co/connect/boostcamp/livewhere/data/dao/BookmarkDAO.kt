package kr.co.connect.boostcamp.livewhere.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity

@Dao
interface BookmarkDAO {
    @Query("SELECT * FROM bookmark")
    fun getAll(): List<BookmarkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmark(bookmark: BookmarkEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmarkAll(bookmark: List<BookmarkEntity>): List<Long>

    @Query("DELETE from Bookmark")
    fun deleteAll(): Int

    @Query("DELETE from Bookmark WHERE address IN (:address)")
    fun deleteBookmark(address: String): Int
}