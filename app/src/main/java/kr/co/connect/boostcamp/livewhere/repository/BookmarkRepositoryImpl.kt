package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Observable
import kr.co.connect.boostcamp.livewhere.data.dao.BookmarkDAO
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
import java.util.concurrent.Callable

class BookmarkRepositoryImpl(private val bookmarkDAO: BookmarkDAO) : BookmarkRepository {
    override fun getBookmark(): Observable<List<BookmarkEntity>> {
        return Observable.fromCallable(object : Callable<List<BookmarkEntity>> {
            override fun call(): List<BookmarkEntity> {
                return bookmarkDAO.getAll()
            }
        })
    }

    override fun setBookmark(bookmarkEntity: BookmarkEntity): Observable<Long> {
        return Observable.fromCallable(object : Callable<Long> {
            override fun call(): Long {
                return bookmarkDAO.insertBookmark(bookmarkEntity)
            }
        })
    }

    override fun deleteBookmark(address: String): Observable<Int> {
        return Observable.fromCallable(object : Callable<Int> {
            override fun call(): Int {
                return bookmarkDAO.deleteBookmark(address)
            }
        })
    }
}
