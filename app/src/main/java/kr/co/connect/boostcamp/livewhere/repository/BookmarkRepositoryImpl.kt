package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Observable
import kr.co.connect.boostcamp.livewhere.data.database.AppDataBase
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity

class BookmarkRepositoryImpl(private val appDataBase: AppDataBase) : BookmarkRepository {
    override fun getBookmark(): Observable<List<BookmarkEntity>> {
        return appDataBase.bookmarkDao().getAll().filter { it.isNotEmpty() }
            .toObservable()
    }

    override fun setBookmark(bookmarkEntity: BookmarkEntity): Observable<Boolean> {
        return Observable.fromCallable {
            appDataBase.bookmarkDao().insertBookmark(bookmarkEntity)
            return@fromCallable true
        }
    }
}