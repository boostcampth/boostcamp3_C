package kr.co.connect.boostcamp.livewhere.ui.main

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
import kr.co.connect.boostcamp.livewhere.data.entity.RecentSearchEntity
import kr.co.connect.boostcamp.livewhere.repository.AutoCompleteRepositoryImpl
import kr.co.connect.boostcamp.livewhere.repository.BookmarkRepositoryImpl
import kr.co.connect.boostcamp.livewhere.repository.RecentSearchRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.util.COUNTRY_CODE
import kr.co.connect.boostcamp.livewhere.util.SingleLiveEvent
import java.util.*

class HomeViewModel(
    private val bookmarkRepositoryImpl: BookmarkRepositoryImpl,
    private val recentSearchRepositoryImpl: RecentSearchRepositoryImpl,
    private val autoCompleteRepositoryImpl: AutoCompleteRepositoryImpl
) : BaseViewModel() {
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

    private lateinit var placesClient: PlacesClient
    private var token = AutocompleteSessionToken.newInstance()

    private val _isRecentSearchVisible = MutableLiveData<Boolean>()
    val isRecentSearchVisible: LiveData<Boolean>
        get() = _isRecentSearchVisible

    private val _autoCompleteLIst = MutableLiveData<List<String>>()
    val autoCompleteList: LiveData<List<String>>
        get() = _autoCompleteLIst

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

    private var _showToast = MutableLiveData<Boolean>()
    val showToast: LiveData<Boolean>
        get() = _showToast

    init {
        getBookmark()
        getRecentSearch()
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
        isEmptyBookmark = checkBookmarkEntity(bookmarkEntity.value.isNullOrEmpty())
    }

    private fun checkBookmarkEntity(value: Boolean): Int {
        return when (value) {
            true -> View.VISIBLE
            false -> View.GONE
        }

    }

    fun setSendText(text: String) {
        _sendAddress.postValue(text)
    }

    fun onBackPressed(): Boolean {
        return if (System.currentTimeMillis() > backKeyPressedTime + TIME_LIMIT) {
            backKeyPressedTime = System.currentTimeMillis()
            true
        } else {
            false
        }
    }

    fun onSearchClicked() {
        _searchBtnClicked.call()
    }

    fun onClickBtn() {
        _btnClicked.call()
    }

    @SuppressLint("CheckResult")
    fun getRecentSearch() {
        getCompositeDisposable().add(
            recentSearchRepositoryImpl.getRecentSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.isNullOrEmpty()) {
                        Log.d("SVM", "postvalue" + it.toString() + "\n" + "before: " + _recentSearch.value.toString())
                        _recentSearch.postValue(it)
                        Log.d("SVM", "after: " + _recentSearch.value.toString())
                    } else {
                        _recentSearch.postValue(it)
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }

    private fun setRecentSearch(text: String) {
        getCompositeDisposable().add(
            recentSearchRepositoryImpl.setRecentSearch(RecentSearchEntity(text))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("SVM", "set RecentSearch")
                    getRecentSearch()
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun setClient(placesClient: PlacesClient) {
        this.placesClient = placesClient
    }

    private fun setVisibility(value: Boolean) {
        _isRecentSearchVisible.postValue(value)
    }

    @SuppressLint("CheckResult")
    fun getTmapApi(text: String) {
        val textList = ArrayList<String>()
        Log.d("HVM", "get Started")
        autoCompleteRepositoryImpl.getAddress(text)
            .subscribe({ response ->
                val addressList = response.body()?.info?.poi?.pois
                if (!addressList.isNullOrEmpty()) {
                    for (item in addressList) {
                        textList.add(item.addressName)
                    }
                }
                if (textList.isNullOrEmpty()) {
                    setVisibility(false)
                } else {
                    setVisibility(true)
                }
                _autoCompleteLIst.postValue(textList.toList())
            },
                {
                    it.printStackTrace()
                })
    }
    fun onClickedMap() {
        _mapBtnClicked.call()
    }

    fun onClickedBack() {
        _backBtnClicked.call()
    }

    fun deleteAll() {
        getCompositeDisposable().add(
            recentSearchRepositoryImpl.deleteRecentSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getRecentSearch()
                }, {
                    it.printStackTrace()
                })
        )
        _showToast.postValue(true)
    }

    fun setToastDone() {
        _showToast.postValue(false)
    }

    fun onClickAutoComplete(text: String) {
        setRecentSearch(text)
        _searchText.postValue(text)
        getRecentSearch()
    }

    fun onRecentSearchClicked(text: String) {
        _searchText.postValue(text)
    }
}