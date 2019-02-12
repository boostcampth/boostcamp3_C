package kr.co.connect.boostcamp.livewhere.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.connect.boostcamp.livewhere.data.dao.BookmarkDAO
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
import kr.co.connect.boostcamp.livewhere.repository.BookmarkRepositoryImpl
import kr.co.connect.boostcamp.livewhere.repository.RecentSearchRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel

class BookmarkViewModel(private val bookmarkRepositoryImpl: BookmarkRepositoryImpl) : BaseViewModel() {
    private val TAG = "BOOKMARK_VIEWMODEL"
    private val _bookmarkEntity = MutableLiveData<List<BookmarkEntity>>()
    val bookmarkEntity: LiveData<List<BookmarkEntity>>
        get() = _bookmarkEntity

    init {
        getBookmark()
    }

    @SuppressLint("CheckResult")
    fun getBookmark() {
        getCompositeDisposable().add(
            bookmarkRepositoryImpl.getBookmark()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it != null) {
                        _bookmarkEntity.postValue(it)
                    }
                }, {

                })
        )
    }
}