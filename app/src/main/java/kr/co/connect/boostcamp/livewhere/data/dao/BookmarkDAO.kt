package kr.co.connect.boostcamp.livewhere.data.dao

import androidx.room.*
import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity

@Dao
interface BookmarkDAO {
    @Query("SELECT * FROM bookmark")
    fun getAll(): Single<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmark(bookmark: BookmarkEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBookmarkAll(bookmark: List<BookmarkEntity>)

    @Query("DELETE from Bookmark")
    fun deleteAll()

    @Query("DELETE from Bookmark WHERE address IN (:address)")
    fun deleteBookmark(address: String)
}