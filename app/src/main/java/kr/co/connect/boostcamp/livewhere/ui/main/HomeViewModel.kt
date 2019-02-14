package kr.co.connect.boostcamp.livewhere.ui.main

import androidx.lifecycle.LiveData
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.util.SingleLiveEvent

class HomeViewModel : BaseViewModel() {
    private var _searchBtnClicked = SingleLiveEvent<Any>()
    val searchBtnClicked: LiveData<Any>
        get() = _searchBtnClicked

    private val _btnClicked = SingleLiveEvent<Any>()
    val btnClicked: LiveData<Any>
        get() = _btnClicked

    init {}

    fun onSearchClicked() {
        _searchBtnClicked.call()
    }



    fun onClickBtn() {
        _btnClicked.call()
    }
}