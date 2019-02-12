package kr.co.connect.boostcamp.livewhere.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import kr.co.connect.boostcamp.livewhere.data.dao.BookmarkDAO
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
import kr.co.connect.boostcamp.livewhere.repository.BookmarkRepositoryImpl
import kr.co.connect.boostcamp.livewhere.repository.RecentSearchRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel

class BookmarkViewModel(private val bookmarkRepositoryImpl: BookmarkRepositoryImpl) : BaseViewModel() {
    private val _bookmarkEntity = MutableLiveData<List<BookmarkEntity>>()
    val bookmarkEntity: LiveData<List<BookmarkEntity>>
        get() = _bookmarkEntity

    init { }

    @SuppressLint("CheckResult")
    fun getBookmark() {
        val run = Runnable {
            bookmarkRepositoryImpl.getBookmark().subscribe {
                _bookmarkEntity.postValue(it)
            }
        }
        val thread = Thread(run)
        thread.start()
    }
}