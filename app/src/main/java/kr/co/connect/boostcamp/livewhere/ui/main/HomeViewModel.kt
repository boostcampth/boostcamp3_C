package kr.co.connect.boostcamp.livewhere.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
import kr.co.connect.boostcamp.livewhere.data.entity.RecentSearchEntity
import kr.co.connect.boostcamp.livewhere.repository.AutoCompleteRepositoryImpl
import kr.co.connect.boostcamp.livewhere.repository.BookmarkRepositoryImpl
import kr.co.connect.boostcamp.livewhere.repository.RecentSearchRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.util.LAT
import kr.co.connect.boostcamp.livewhere.util.LON
import kr.co.connect.boostcamp.livewhere.util.SingleLiveEvent
import kr.co.connect.boostcamp.livewhere.util.TIME_LIMIT
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class HomeViewModel(
    private val bookmarkRepositoryImpl: BookmarkRepositoryImpl,
    private val recentSearchRepositoryImpl: RecentSearchRepositoryImpl,
    private val autoCompleteRepositoryImpl: AutoCompleteRepositoryImpl
) : BaseViewModel() {
    private var _hideKeyboard = MutableLiveData<Boolean>()
    val hideKeyboard: LiveData<Boolean>
        get() = _hideKeyboard

    private var _searchBtnClicked = SingleLiveEvent<Any>()
    val searchBtnClicked: LiveData<Any>
        get() = _searchBtnClicked

    private var _latitude = MutableLiveData<HashMap<String, String>>()
    val latitude: LiveData<HashMap<String, String>>
        get() = _latitude

    private var _longitude = MutableLiveData<HashMap<String, String>>()
    val longitude: LiveData<HashMap<String, String>>
        get() = _longitude

    private val _btnClicked = SingleLiveEvent<Any>()
    val btnClicked: LiveData<Any>
        get() = _btnClicked

    private var backKeyPressedTime: Long = 0

    var isEmptyBookmark: Int = View.VISIBLE
    private val subject: Subject<HashMap<String, String>> = PublishSubject.create()
    private val recentSubject: Subject<HashMap<String, String>> = PublishSubject.create()

    private val _bookmarkEntity = MutableLiveData<List<BookmarkEntity>>()
    val bookmarkEntity: LiveData<List<BookmarkEntity>>
        get() = _bookmarkEntity

    private val _isRecentSearchVisible = MutableLiveData<Boolean>()
    val isRecentSearchVisible: LiveData<Boolean>
        get() = _isRecentSearchVisible

    private val _autoCompleteLIst = MutableLiveData<List<String>>()
    val autoCompleteList: LiveData<List<String>>
        get() = _autoCompleteLIst

    private val _searchMap = MutableLiveData<HashMap<String, String>>()
    val searchMap: LiveData<HashMap<String, String>>
        get() = _searchMap

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

    private var _bookmarkMap = MutableLiveData<HashMap<String, String>>()
    val bookmarkMap: LiveData<HashMap<String, String>>
        get() = _bookmarkMap

    init {
        addDisposable(
            recentSubject.throttleFirst(1, TimeUnit.SECONDS)
                .subscribe({
                    _searchMap.postValue(it)
                }, {
                    it.printStackTrace()
                })
        )

        addDisposable(
            subject.throttleFirst(1, TimeUnit.SECONDS)
                .subscribe({
                    _searchMap.postValue(it)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun getBookmark() {
        getCompositeDisposable().add(
            bookmarkRepositoryImpl.getBookmark()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _bookmarkEntity.postValue(it)
                }, {
                    it.printStackTrace()
                })
        )
        isEmptyBookmark = checkBookmarkEntity(bookmarkEntity.value.isNullOrEmpty())
    }

    fun onBookmarkClicked(lat: String, lon: String) {
        subject.onNext(makeLonLatMap(lat, lon))
    }

    private fun checkBookmarkEntity(value: Boolean): Int {
        return when (value) {
            true -> View.VISIBLE
            false -> View.GONE
        }
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

    fun getRecentSearch() {
        getCompositeDisposable().add(
            recentSearchRepositoryImpl.getRecentSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.isNullOrEmpty()) {
                        _recentSearch.postValue(it)
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
            recentSearchRepositoryImpl.setRecentSearch(
                RecentSearchEntity(
                    text,
                    longitude.value!![text]!!,
                    latitude.value!![text]!!
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getRecentSearch()
                }, {
                    it.printStackTrace()
                })
        )
    }

    private fun setVisibility(value: Boolean) {
        _isRecentSearchVisible.postValue(value)
    }

    fun getTmapApi(text: String) {
        val textList = ArrayList<String>()
        val latList = HashMap<String, String>()
        val lonList = HashMap<String, String>()

        addDisposable(
            autoCompleteRepositoryImpl.getAddress(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe({
                    val addressList = it.body()?.info?.poi?.pois
                    if (!addressList.isNullOrEmpty()) {
                        for (item in addressList) {
                            textList.add(item.addressName)
                            latList[item.addressName] = item.latitude
                            lonList[item.addressName] = item.longitude
                        }
                    }
                    if (textList.isNullOrEmpty()) {
                        setVisibility(false)
                    } else {
                        setVisibility(true)
                    }
                    _latitude.postValue(latList)
                    _longitude.postValue(lonList)
                    _autoCompleteLIst.postValue(textList.toList())
                }, {
                    it.printStackTrace()
                })
        )
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
        _searchMap.postValue(getLonLat(text))
        getRecentSearch()
    }

    private fun getLonLat(text: String): HashMap<String, String> {
        return makeLonLatMap(latitude.value!![text]!!, longitude.value!![text]!!)
    }

    private fun makeLonLatMap(lat: String, lon: String): HashMap<String, String> {
        val map = HashMap<String, String>()
        map[LAT] = lat
        map[LON] = lon
        return map
    }


    fun onRecentSearchClicked(lat: String, lon: String) {
        recentSubject.onNext(makeLonLatMap(lat, lon))
    }

    fun setHideKeyboard(value: Boolean) {
        _hideKeyboard.postValue(value)
    }
}