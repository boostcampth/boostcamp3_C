package kr.co.connect.boostcamp.livewhere.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity

@Dao
interface BookmarkDAO {
    @Query("SELECT * FROM Bookmark")
    fun getAll(): List<BookmarkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE from Bookmark")
    fun deleteAll()

    @Query("DELETE from Bookmark WHERE address = :address")
    fun deleteBookmark(address: String)
}