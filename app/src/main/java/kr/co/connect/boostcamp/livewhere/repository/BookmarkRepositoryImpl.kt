package kr.co.connect.boostcamp.livewhere.repository

import android.util.Log
import io.reactivex.Observable
import kr.co.connect.boostcamp.livewhere.data.dao.BookmarkDAO
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
import java.util.concurrent.Callable

class BookmarkRepositoryImpl(private val bookmarkDAO: BookmarkDAO) : BookmarkRepository {
    override fun getBookmark():Observable<List<BookmarkEntity>> {
        return Observable.fromCallable(object: Callable<List<BookmarkEntity>> {
            override fun call():List<BookmarkEntity> {
                return bookmarkDAO.getAll()
            }
        })
    }

    override fun setBookmark(bookmarkEntity: BookmarkEntity): Boolean {
        //TODO: Thread Handle
        var runnable = Runnable {
            bookmarkDAO.insertBookmark(bookmarkEntity)
        }
        val thread = Thread(runnable)
        thread.start()
        return true
    }

    override fun deleteBookmark(address: String): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}