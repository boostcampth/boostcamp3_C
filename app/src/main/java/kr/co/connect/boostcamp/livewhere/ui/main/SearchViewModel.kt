package kr.co.connect.boostcamp.livewhere.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.connect.boostcamp.livewhere.data.entity.RecentSearchEntity
import kr.co.connect.boostcamp.livewhere.repository.RecentSearchRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.util.SingleLiveEvent

class SearchViewModel(
    private val recentSearchRepositoryImpl: RecentSearchRepositoryImpl
) : BaseViewModel() {
    private lateinit var placesClient: PlacesClient
    private val TAG = "SEARCH_VIEW_MODEL"
    private val COUNTY_CODE = "kr"
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

    fun setClient(placesClient: PlacesClient) {
        this.placesClient = placesClient
    }

    fun startAutoComplete(text: String) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setCountry(COUNTY_CODE)
            .setTypeFilter(TypeFilter.GEOCODE)
            .setSessionToken(token)
            .setQuery(text)
            .build()

        val textList = ArrayList<String>()
        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                for (prediction in response.autocompletePredictions) {
                    Log.d(TAG, "결과: " + prediction.getPrimaryText(null).toString())
                    Log.d(TAG, "결과2: " + prediction.getFullText(null).toString())
                    Log.d(TAG, "결과3: " + prediction.getSecondaryText(null).toString())
                    textList.add(prediction.getFullText(null).toString())
                }
                _autoCompleteLIst.postValue(textList.toList())
                if (!textList.isEmpty()) {
                    _isRecentSearchVisible.postValue(false)
                } else {
                    _isRecentSearchVisible.postValue(true)
                }
            }
            .addOnFailureListener { exception ->
                if (exception is ApiException) {
                    Log.e(TAG, "Place not found: " + exception.statusCode)
                }
            }
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

    fun deleteAll() {
        recentSearchRepositoryImpl.deleteRecentSearch()
        getRecentSearch()
    }
}