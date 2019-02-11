package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Observable
import kr.co.connect.boostcamp.livewhere.data.dao.BookmarkDAO
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity

class BookmarkRepositoryImpl(private val bookmarkDAO: BookmarkDAO) : BookmarkRepository {
    override fun getBookmark(): Observable<List<BookmarkEntity>> {
        return Observable.fromCallable {
            return@fromCallable bookmarkDAO.getAll()
        }
    }

    override fun setBookmark(bookmarkEntity: BookmarkEntity): Observable<Boolean> {
        return Observable.fromCallable {
            bookmarkDAO.insertBookmark(bookmarkEntity)
            return@fromCallable true
        }
    }
}