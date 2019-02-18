package kr.co.connect.boostcamp.livewhere.ui.map

import android.content.Context
import android.content.Intent
import android.graphics.PointF
import android.net.Uri
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.CircleOverlay
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.model.*
import kr.co.connect.boostcamp.livewhere.repository.MapRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.ui.detail.DetailActivity
import kr.co.connect.boostcamp.livewhere.ui.map.interfaces.OnMapHistoryListener
import kr.co.connect.boostcamp.livewhere.ui.map.interfaces.OnViewHistoryListener
import kr.co.connect.boostcamp.livewhere.util.RADIUS
import kr.co.connect.boostcamp.livewhere.util.StatusCode
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit

interface OnMapViewModelInterface : NaverMap.OnMapLongClickListener, OnMapReadyCallback,
    View.OnClickListener, OnMapHistoryListener, OnViewHistoryListener {
    fun onClickMapImageView(view: View, liveData: LiveData<*>)
    fun putPressedLiveData(tick: Long)
    fun onLaunchUrl(context: Context, url: String)
    fun onStartDetailActivity(context: Context, markerInfo: MarkerInfo)
    fun onNextStartActivity(view: View, markerInfo: MarkerInfo)
    fun onRemoveDisposable(disposable: Disposable)
    fun onClickStreetImageView(view:View,lat:String, lng:String, address:String)
}

class MapViewModel(val mapRepository: MapRepositoryImpl) : BaseViewModel(),
    OnMapViewModelInterface {

    //현재 검색하려는 매물의 좌표 livedata
    private val _markerLiveData: MutableLiveData<MarkerInfo> = MutableLiveData()
    val markerLiveData: LiveData<MarkerInfo>
        get() = _markerLiveData

    private val _markerImageLiveData: MutableLiveData<MarkerInfo> = MutableLiveData()
    val markerImageLiveData: LiveData<MarkerInfo>
        get() = _markerImageLiveData

    //현재 사용하고 있는 naverMap의 status livedata
    private val _mapStatusLiveData: MutableLiveData<NaverMap> = MutableLiveData()
    val mapStatusLiveData: LiveData<NaverMap>
        get() = _mapStatusLiveData

    //상권 정보에 대한 LiveData
    private val _placeResponseLiveData: MutableLiveData<PlaceResponse> = MutableLiveData()
    val placeResponseLiveData: LiveData<PlaceResponse>
        get() = _placeResponseLiveData

    //하나의 상권의 정보를 가져오기 위한 LiveData
    private val _placeMarkerLiveData: MutableLiveData<Place> = MutableLiveData()
    val placeMarkerLiveData: LiveData<Place>
        get() = _placeMarkerLiveData

    //결과 값을 보여주는 RecyclerView에 들어가는 LiveData,
    //List<Place>, List<House>, List<Empty>의 data가 들어옴
    private val _searchListLiveData: MutableLiveData<List<Any>> = MutableLiveData()
    val searchListLiveData: LiveData<List<Any>>
        get() = _searchListLiveData

    //사용자의 상태를 체크하는 liveData, 값에 따라서 백드롭의 타이틀 변경에 사용
    private val _userStatusLiveData: MutableLiveData<UserStatus> = MutableLiveData()
    val userStatusLiveData: LiveData<UserStatus>
        get() = _userStatusLiveData

    //삭제할 PlaceMarker Livedata
    private val _removePlaceMarkersLiveData: MutableLiveData<MutableList<Marker>> = MutableLiveData()

    //PlaceMarker의 정보를 저장해두는 LiveData
    private val _savePlaceMarkersLiveData: MutableLiveData<MutableList<Marker>> = MutableLiveData()
    val removePlaceMarkersLiveData: LiveData<MutableList<Marker>>
        get() = _removePlaceMarkersLiveData

    //이전 overlay의 정보를 갖고 있는 LiveData
    private val _tempOverlayLiveData: MutableLiveData<CircleOverlay> = MutableLiveData()
    val tempOverlayLiveData: LiveData<CircleOverlay>
        get() = _tempOverlayLiveData

    //현재 overlay의 정보를 갖고 있는 LiveData
    private val _currentOverlayLiveData: MutableLiveData<CircleOverlay> = MutableLiveData()
    val currentOverlayLiveData: LiveData<CircleOverlay>
        get() = _currentOverlayLiveData

    private val _tempInfoWindowLiveData: MutableLiveData<InfoWindow> = MutableLiveData()
    val tempInfoWindowLiveData: LiveData<InfoWindow>
        get() = _tempInfoWindowLiveData

    private val _currentInfoWindowLiveData: MutableLiveData<InfoWindow> = MutableLiveData()
    val currentInfoWindowLiveData: LiveData<InfoWindow>
        get() = _currentInfoWindowLiveData

    private val _cameraPositionLatLngLiveData: MutableLiveData<CameraPositionInfo> = MutableLiveData()
    val cameraPositionLatLngLiveData: LiveData<CameraPositionInfo>
        get() = _cameraPositionLatLngLiveData

    private val _pressedImageViewTimeLiveData: MutableLiveData<Long> = MutableLiveData()

    val pressedImageViewTimeLiveData: LiveData<Long>
        get() = _pressedImageViewTimeLiveData

    private var timerObservable: Disposable? = null

    private val startActivityBehaviorSubject = BehaviorSubject.createDefault(0L)
    private var startDetailActivityObservable: Disposable? = null

    override fun putPressedLiveData(tick: Long) {
        _pressedImageViewTimeLiveData.postValue(tick)
    }

    override fun startObservable(title: String, toolbar: Toolbar) {
        timerObservable =
            Observable.interval(0, 1, TimeUnit.SECONDS)
                .map { timer -> timer % 3 }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { tick ->
                    val dotText = when (tick.toInt()) {
                        0 -> ""
                        1 -> "."
                        2 -> ".."
                        else -> ""
                    }
                    toolbar.title = title + dotText
                }
        addDisposable(timerObservable!!)
    }

    override fun stopObservable() {
        onCleared()
    }

    override fun onMoveCameraPosition(latLng: LatLng, zoom: Double) {
        val cameraPositionInfo = CameraPositionInfo(latLng, zoom)
        _cameraPositionLatLngLiveData.postValue(cameraPositionInfo)
    }

    override fun onRemoveInfoWindow() {
        _tempInfoWindowLiveData.postValue(currentInfoWindowLiveData.value)

    }

    override fun onSaveInfoWindow(infoWindow: InfoWindow) {
        _currentInfoWindowLiveData.postValue(infoWindow)
    }

    override fun onSaveCircleOverlay(circleOverlay: CircleOverlay) {
        _currentOverlayLiveData.postValue(circleOverlay)
    }

    override fun onRemoveCircleOverlay() {
        _tempOverlayLiveData.postValue(currentOverlayLiveData.value)
    }


    override fun onSaveFilterMarker(markerList: MutableList<Marker>) {
        _savePlaceMarkersLiveData.postValue(markerList)
    }

    override fun onRemoveFilterMarker() {
        _removePlaceMarkersLiveData.postValue(_savePlaceMarkersLiveData.value)
    }

    override fun onClickMarkerPlace(place: Place) {
        _placeMarkerLiveData.postValue(place)
    }

    override fun onClickMarkerHouse(house: MarkerInfo) {
        _markerImageLiveData.postValue(house)
    }

    override fun onClick(view: View?) {
        _userStatusLiveData.postValue(UserStatus(StatusCode.BEFORE_SEARCH_PLACE, ""))
        val currentMarkerInfo = markerLiveData.value
        val category = when {
            view?.id == R.id.fab_filter_cafe -> "CE7"
            view?.id == R.id.fab_filter_food -> "FD6"
            view?.id == R.id.fab_filter_hospital -> "HP8"
            view?.id == R.id.fab_filter_school -> "PS3,SC4"
            view?.id == R.id.fab_filter_store -> "MT1,CS2"
            else -> ""
        }

        if (currentMarkerInfo != null) {
            val latLng = currentMarkerInfo.latLng
            val searchDisposable = mapRepository.getPlace(latLng.latitude, latLng.longitude, RADIUS, category).subscribe({ response ->
                val placeResponse = response.body()
                val placeList = placeResponse?.placeList
                Collections.sort(placeList as List<Place>) { o1, o2 -> o1.distance.toInt() - o2.distance.toInt() }
                _placeResponseLiveData.postValue(placeResponse)
                _searchListLiveData.postValue(placeList)
                if (placeList.isEmpty()) {
                    _userStatusLiveData.postValue(UserStatus(StatusCode.EMPTY_SEARCH_PLACE, ""))
                } else {
                    _userStatusLiveData.postValue(
                        UserStatus(
                            StatusCode.SUCCESS_SEARCH_PLACE,
                            String.format(
                                view?.context!!.getString(R.string.info_success_search_place_text),
                                placeList[0].category,
                                placeList.size
                            )
                        )
                    )
                }
            }, {
                _userStatusLiveData.postValue(UserStatus(StatusCode.FAILURE_SEARCH_PLACE, ""))
            })
        } else {
            _userStatusLiveData.postValue(UserStatus(StatusCode.EMPTY_HOUSE_TARGET, ""))
        }
    }

    override fun onLoadBuildingList(anyList: List<Any>, view: View) {
        if (anyList.size == 1 && anyList[0] is MarkerInfo) {
            val markerInfo = anyList[0] as MarkerInfo
            if (!markerInfo.houseList.isNullOrEmpty()) {
                _searchListLiveData.postValue(anyList)
                _userStatusLiveData.postValue(
                    UserStatus(
                        StatusCode.SUCCESS_SEARCH_HOUSE,
                        markerInfo.address.addr + "\n" + markerInfo.houseList[0].name
                    )
                )
            } else {
                _searchListLiveData.postValue(listOf(EmptyInfo(markerInfo.address.addr)))
                _userStatusLiveData.postValue(UserStatus(StatusCode.EMPTY_SEARCH_HOUSE, ""))
            }
        } else if (anyList.size > 1 && anyList[0] is MarkerInfo) {
            val markerInfo = anyList[0] as MarkerInfo
            _searchListLiveData.postValue(anyList)
            _userStatusLiveData.postValue(
                UserStatus(
                    StatusCode.SUCCESS_SEARCH_HOUSE,
                    markerInfo.address.addr + "\n" + markerInfo.houseList[0].name
                )
            )
        } else if (anyList.size > 1 && anyList[0] is Place) {
            val placeInfo = anyList[0] as Place
            _searchListLiveData.postValue(anyList)
            _userStatusLiveData.postValue(
                UserStatus(
                    StatusCode.SUCCESS_SEARCH_PLACE,
                    String.format(
                        view.context!!.getString(R.string.info_success_search_place_text),
                        placeInfo.category,
                        anyList.size
                    )
                )
            )
        } else if (anyList.isEmpty()) {
            _searchListLiveData.postValue(listOf(EmptyInfo("")))
            _userStatusLiveData.postValue(UserStatus(StatusCode.EMPTY_SEARCH_PLACE, ""))
        } else {
            _searchListLiveData.postValue(anyList)
        }
    }


    override fun onMapLongClick(point: PointF, latLng: LatLng) {
        _userStatusLiveData.postValue(
            UserStatus(
                StatusCode.SEARCH_HOUSE,
                "${latLng.latitude.toString().substring(0, 10)}, ${latLng.longitude.toString().substring(0, 10)}"
            )
        )
        loadHousePrice(latLng)
    }

    override fun onMapReady(naverMap: NaverMap) {
        _mapStatusLiveData.postValue(naverMap)
        _userStatusLiveData.postValue(UserStatus(StatusCode.DEFAULT_SEARCH, ""))
    }

    private fun loadHousePrice(latLng: LatLng) = mapRepository
        .getAddress(latLng.latitude.toString(), latLng.longitude.toString(), "WGS84")
        .flatMap { address ->
            if (address.isSuccessful && address.body()?.metaData?.total_count!! >= 1) {
                mapRepository.getHouseDetail(address.body()?.documentData!![0].addressMeta.addressName)
                    .map { response -> Pair(response, address.body()?.documentData!![0].addressMeta.addressName) }
            } else {
                mapRepository.getHouseDetail(address.body()?.metaData?.total_count.toString())
                    .map { response -> Pair(response, address.body()?.documentData!![0].addressMeta.addressName) }
            }
        }
        .flatMap { pair ->
            val response = pair.first
            val address = pair.second
            val houseResponse = response.body()
            if (houseResponse?.addrStatusCode == StatusCode.RESULT_200.response
                && houseResponse.houseStatusCode == StatusCode.RESULT_200.response
            ) {
                Single.just(Pair(address, houseResponse))
            } else {
                Single.just(Pair(address, houseResponse))
            }
        }
        .subscribe({ result ->
            val address = result.first
            val response = result.second
            if (response?.houseStatusCode == 200 && response.addrStatusCode == 200) {
                val houseList = response.houseList
                val currentMarkerInfo = MarkerInfo(response.addr, latLng, houseList, StatusCode.RESULT_200)
                _userStatusLiveData.postValue(
                    UserStatus(
                        StatusCode.SUCCESS_SEARCH_HOUSE,
                        address + "\n" + houseList[0].name
                    )
                )
                _searchListLiveData.postValue(listOf(currentMarkerInfo))
                _markerLiveData.postValue(currentMarkerInfo)
            } else {
                val currentMarkerInfo = MarkerInfo(response?.addr!!, latLng, emptyList(), StatusCode.RESULT_204)
                _userStatusLiveData.postValue(UserStatus(StatusCode.EMPTY_SEARCH_HOUSE, address))
                _searchListLiveData.postValue(listOf(EmptyInfo(address)))
                _markerLiveData.postValue(currentMarkerInfo)
            }
        }, {
            _userStatusLiveData.postValue(UserStatus(StatusCode.FAILURE_SEARCH_HOUSE, ""))
        })

    override fun onClickMapImageView(view: View, liveData: LiveData<*>) {
        var lat: String = ""
        var lng: String = ""
        var address: String = ""
        if (liveData.value is MarkerInfo) {
            val markerInfo = liveData.value as MarkerInfo
            lat = markerInfo.latLng.latitude.toString()
            lng = markerInfo.latLng.longitude.toString()
            address = markerInfo.address.name
        } else if (liveData.value is Place) {
            val placeInfo = liveData.value as Place
            lat = placeInfo.y
            lng = placeInfo.x
            address = placeInfo.addrName
        }
        putPressedLiveData(System.currentTimeMillis())
        view.setOnClickListener {
            if (pressedImageViewTimeLiveData.value != null &&
                System.currentTimeMillis() - pressedImageViewTimeLiveData.value!! > 1000
            ) {
                onClickStreetImageView(view, lat, lng, address)//이미지뷰 클릭시에 일어나는 로직
            }
        }
    }

    override fun onSearchHouseWithAddress(notCompletedAddress: String) {
        mapRepository.getHouseDetailWithNotCompletedAddress(notCompletedAddress)
            .flatMap { response: Response<HouseResponse> ->
                val houseResponse = response.body()
                if (houseResponse?.addrStatusCode == StatusCode.RESULT_200.response
                    && houseResponse.houseStatusCode == StatusCode.RESULT_200.response
                ) {
                    Single.just(Pair(houseResponse.addr.name, houseResponse))
                } else if (houseResponse?.addrStatusCode == StatusCode.RESULT_200.response
                    && houseResponse.houseStatusCode == StatusCode.RESULT_204.response
                ) {
                    Single.just(Pair(houseResponse.addr.name, houseResponse))
                } else {
                    Single.just(Pair(notCompletedAddress, houseResponse))
                }
            }.flatMap { pair ->
                val address = pair.first
                val houseResponse = pair.second
                if (houseResponse?.addrStatusCode == StatusCode.RESULT_200.response
                    && houseResponse.houseStatusCode == StatusCode.RESULT_200.response
                ) {
                    Single.just(Pair(address, houseResponse))
                } else {
                    Single.just(Pair(address, houseResponse))
                }
            }.subscribe({ result ->
                val address = result.first
                val response = result.second

                if (response?.houseStatusCode == 200 && response.addrStatusCode == 200) {
                    val latLng = LatLng(response?.addr?.y?.toDouble()!!, response?.addr?.x.toDouble())
                    val houseList = response.houseList
                    val currentMarkerInfo = MarkerInfo(response.addr, latLng, houseList, StatusCode.RESULT_200)
                    _userStatusLiveData.postValue(
                        UserStatus(
                            StatusCode.SUCCESS_SEARCH_HOUSE,
                            address + "\n" + houseList[0].name
                        )
                    )
                    _searchListLiveData.postValue(listOf(currentMarkerInfo))
                    _markerLiveData.postValue(currentMarkerInfo)
                } else if (response?.houseStatusCode == 204 && response.addrStatusCode == 200) {
                    val latLng = LatLng(response.addr.y.toDouble(), response.addr.x.toDouble())
                    val currentMarkerInfo = MarkerInfo(response?.addr!!, latLng, emptyList(), StatusCode.RESULT_204)
                    _userStatusLiveData.postValue(UserStatus(StatusCode.EMPTY_SEARCH_HOUSE, address))
                    _searchListLiveData.postValue(listOf(EmptyInfo(address)))
                    _markerLiveData.postValue(currentMarkerInfo)
                } else {
                    _userStatusLiveData.postValue(UserStatus(StatusCode.EMPTY_SEARCH_HOUSE, address))
                    _searchListLiveData.postValue(listOf(EmptyInfo(address)))
                }
            }, {
                _userStatusLiveData.postValue(UserStatus(StatusCode.FAILURE_SEARCH_HOUSE, ""))
            })
    }

    override fun onInitActivityStatus() {
        _userStatusLiveData.postValue(UserStatus(StatusCode.DEFAULT_SEARCH, ""))
    }

    override fun onStartDetailActivity(context: Context, markerInfo: MarkerInfo) {
        startDetailActivityObservable = startActivityBehaviorSubject
            .buffer(2, 1)
            .observeOn(AndroidSchedulers.mainThread())
            .filter { pair -> pair[1] - pair[0] > 1000 }
            .subscribe { _ ->
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("markerInfo", markerInfo)
                context.startActivity(intent)
            }

        addDisposable(startDetailActivityObservable!!)
    }

    override fun onLaunchUrl(context: Context, url: String) {
        if (pressedImageViewTimeLiveData.value != null && System.currentTimeMillis() - pressedImageViewTimeLiveData.value!! > 1000) {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(url))
            putPressedLiveData(System.currentTimeMillis())
        }
    }

    override fun onNextStartActivity(view: View, markerInfo: MarkerInfo) {
        startActivityBehaviorSubject.onNext(System.currentTimeMillis())
    }

    override fun onRemoveDisposable(disposable: Disposable) {
        disposable.dispose()
        getCompositeDisposable().remove(disposable)
    }

    override fun onClickStreetImageView(view:View,lat: String, lng: String, address: String) {
        val intent = Intent(view.context, StreetMapActivity::class.java)
        intent.putExtra("lat", lat)
        intent.putExtra("lng", lng)
        intent.putExtra("address", address)
        putPressedLiveData(System.currentTimeMillis())
        view.context.startActivity(intent)
    }
}