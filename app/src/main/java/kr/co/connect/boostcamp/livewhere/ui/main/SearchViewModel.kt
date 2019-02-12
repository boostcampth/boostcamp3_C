package kr.co.connect.boostcamp.livewhere.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.connect.boostcamp.livewhere.data.entity.RecentSearchEntity
import kr.co.connect.boostcamp.livewhere.repository.RecentSearchRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.util.SingleLiveEvent

class SearchViewModel(val recentSearchRepositoryImpl: RecentSearchRepositoryImpl) : BaseViewModel() {
    private val TAG = "SEARCH_VIEW_MODEL"

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String>
        get() = _searchText

    private val _recentSearch = MutableLiveData<List<RecentSearchEntity>>()
    val recentSearch: LiveData<List<RecentSearchEntity>>
        get() = _recentSearch

    private var _mapBtnClicked = SingleLiveEvent<Any>()
    val mapBtnClicked: LiveData<Any>
        get() = _mapBtnClicked

    private var _backBtnClicked = SingleLiveEvent<Any>()
    val backBtnClicked: LiveData<Any>
        get() = _backBtnClicked

    init {
        getRecentSearch()
    }

    @SuppressLint("CheckResult")
    fun getRecentSearch() {
        getCompositeDisposable().add(
            recentSearchRepositoryImpl.getRecentSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it != null) {
                        _recentSearch.postValue(it)
                    }
                }, {

                })
        )
    }

    fun onFinishSearch(text: String) {
        recentSearchRepositoryImpl.setRecentSearch(RecentSearchEntity(text))
        _searchText.postValue(text)
    }

    fun onClickedMap() {
        _mapBtnClicked.call()
    }

    fun onClickedBack() {
        _backBtnClicked.call()
    }
}