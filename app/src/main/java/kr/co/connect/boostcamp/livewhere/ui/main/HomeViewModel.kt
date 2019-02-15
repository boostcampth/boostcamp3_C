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

    private var backKeyPressedTime: Long = 0
    private val TIME_LIMIT = 2000

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