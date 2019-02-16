package kr.co.connect.boostcamp.livewhere.ui.main

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.libraries.places.internal.it
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
import kr.co.connect.boostcamp.livewhere.repository.BookmarkRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.util.SingleLiveEvent

class HomeViewModel(private val bookmarkRepositoryImpl: BookmarkRepositoryImpl) : BaseViewModel() {
    private var _searchBtnClicked = SingleLiveEvent<Any>()
    val searchBtnClicked: LiveData<Any>
        get() = _searchBtnClicked

    private val _btnClicked = SingleLiveEvent<Any>()
    val btnClicked: LiveData<Any>
        get() = _btnClicked

    private var backKeyPressedTime: Long = 0
    private val TIME_LIMIT = 2000

    var isEmptyBookmark: Int = View.VISIBLE

    private val _bookmarkEntity = MutableLiveData<List<BookmarkEntity>>()
    val bookmarkEntity: LiveData<List<BookmarkEntity>>
        get() = _bookmarkEntity

    private val _sendAddress = MutableLiveData<String>()
    val sendAddress: LiveData<String>
        get() = _sendAddress

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
                    _bookmarkEntity.postValue(it)
                }, {

                })
        )
        if(bookmarkEntity.value.isNullOrEmpty()) {
            isEmptyBookmark = View.VISIBLE
        } else {
            isEmptyBookmark = View.GONE
        }
    }

    fun setSendText(text: String) {
        _sendAddress.postValue(text)
    }

    fun onBackPressed(): Boolean {
        if(System.currentTimeMillis() > backKeyPressedTime + TIME_LIMIT) {
            backKeyPressedTime = System.currentTimeMillis()
            return true
        }
        else {
            return false
        }
    }

    init {
    }

    fun onSearchClicked() {
        _searchBtnClicked.call()
    }

    fun onClickBtn() {
        _btnClicked.call()
    }


}