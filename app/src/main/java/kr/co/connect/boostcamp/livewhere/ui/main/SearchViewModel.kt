package kr.co.connect.boostcamp.livewhere.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.model.RecentSearch
import kr.co.connect.boostcamp.livewhere.repository.RecentSearchRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.util.SingleLiveEvent

class SearchViewModel(val recentSearchRepositoryImpl: RecentSearchRepositoryImpl) : BaseViewModel() {

    private val _recentSearch = MutableLiveData<ArrayList<RecentSearch>>()
    val recentSearch: LiveData<ArrayList<RecentSearch>>
        get() = _recentSearch

    private var _mapBtnClicked = SingleLiveEvent<Any>()
    val mapBtnClicked: LiveData<Any>
        get() = _mapBtnClicked

    private var _backBtnClicked = SingleLiveEvent<Any>()
    val backBtnClicked: LiveData<Any>
        get() = _backBtnClicked

    init {
        val temprecentsearchvalue = arrayListOf<RecentSearch>(
            RecentSearch("서울특별시 동대문구 제기동 133")
        )
        _recentSearch.postValue(temprecentsearchvalue)
    }

    fun onClickedMap() {
        _mapBtnClicked.call()
    }

    fun onClickedBack() {
        _backBtnClicked.call()
    }
}