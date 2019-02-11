package kr.co.connect.boostcamp.livewhere.data.database

import android.content.Context
import androidx.annotation.NonNull
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kr.co.connect.boostcamp.livewhere.data.dao.BookmarkDAO
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
/*

@Database(entities = [BookmarkEntity::class], version = 1)
abstract class BookmarkDB : RoomDatabase() {
    companion object {
        private const val TAG = "RoomDatabase"

        private var instance: BookmarkDB? = null

        @VisibleForTesting
        private val DATABASE_NAME = "bookmark.db"

        fun getInstance(context: Context, executers: AppExecutors): BookmarkDB? {
            if (instance == null) {
                synchronized(BookmarkDB::class) {
                    instance = buildDatabase(context.applicationContext, executers )
                    instance.updataDatabaseCreated(context.applicationContext)
                }
            }
            return instance
        }

        private fun buildDatabase(appContext: Context, executors: AppExecutors): BookmarkDB? {
            return Room.databaseBuilder(appContext, BookmarkDB::class.java, DATABASE_NAME)
                .addCallback(object:Callback() {
                    override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                        executors.diskIO().execute({
                            addDelay()
                            val database: BookmarkDB? = BookmarkDB.getInstance(appContext, executors)
                            database?.setDatabaseCreated()
                        })
                    }
                }).build()
        }

        private fun insertData(database: BookmarkDB, products: List<BookmarkEntity>) {
            database.runInTransaction({
                database.BookmarkDAO().insertBookmarkAll(products)
            })
        }

        private fun addDelay() {
            try {
                Thread.sleep(4000)
            } catch (ignored: InterruptedException) {
                //TODO: Exception Handler
            }
        }
    }

    abstract fun BookmarkDAO(): BookmarkDAO
    private val isDatabaseCreated: MutableLiveData<Boolean> = MutableLiveData()

    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    private fun setDatabaseCreated() {
        isDatabaseCreated.postValue(true)
    }

    fun getDatabaseCreated(): LiveData<Boolean> {
        return isDatabaseCreated
    }

    fun destoryInstance() {
        instance = null
    }
}*/
