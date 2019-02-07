package kr.co.connect.boostcamp.livewhere.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.co.connect.boostcamp.livewhere.model.RecentSearch
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.util.SingleLiveEvent

class SearchViewModel() : BaseViewModel() {

    private val _recentSearch = MutableLiveData<ArrayList<RecentSearch>>()
    val recentSearch: LiveData<ArrayList<RecentSearch>>
        get() = _recentSearch

    private var _searchBtnClicked = SingleLiveEvent<Any>()
    val searchBtnClicked: LiveData<Any>
        get() = _searchBtnClicked

    private var _mapBtnClicked = SingleLiveEvent<Any>()
    val mapBtnClicked: LiveData<Any>
        get() = _mapBtnClicked

    init {
        val temprecentsearchvalue = arrayListOf<RecentSearch>(
            RecentSearch("서울특별시 동대문구 제기동 133")
        )
        _recentSearch.postValue(temprecentsearchvalue)
    }

    private fun searchClicked() {
        _searchBtnClicked.call()
    }

    private fun mapClicked() {
        _mapBtnClicked.call()
    }
}