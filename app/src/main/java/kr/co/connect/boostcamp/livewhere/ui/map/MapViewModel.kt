package kr.co.connect.boostcamp.livewhere.ui.map

import android.graphics.PointF
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.model.*
import kr.co.connect.boostcamp.livewhere.repository.MapRepositoryImpl
import kr.co.connect.boostcamp.livewhere.util.MapUtilImpl
import kr.co.connect.boostcamp.livewhere.util.RADIUS
import kr.co.connect.boostcamp.livewhere.util.StatusCode

class MapViewModel(val mapUtilImpl: MapUtilImpl, val mapRepository: MapRepositoryImpl) : ViewModel(),
    NaverMap.OnMapLongClickListener, OnMapReadyCallback, View.OnClickListener, OnMarkerListener, OnSearchTrigger {

    private val _markerLiveData: MutableLiveData<MarkerInfo> = MutableLiveData()
    //현재 검색하려는 house의 좌표 livedata
    val markerLiveData: LiveData<MarkerInfo>
        get() = _markerLiveData

    private val _filterMarkerLiveData: MutableLiveData<MarkerInfo> = MutableLiveData()
    //현재 검색하려는 house의 좌표 livedata
    val filterMarkerLiveData: LiveData<MarkerInfo>
        get() = _filterMarkerLiveData

    //현재 사용하고 있는 naverMap의 status livedata
    private val _mapStatusLiveData: MutableLiveData<NaverMap> = MutableLiveData()
    val mapStatusLiveData: LiveData<NaverMap>
        get() = _mapStatusLiveData

    private val _placeResponseLiveData: MutableLiveData<PlaceResponse> = MutableLiveData()
    val placeResponseLiveData: LiveData<PlaceResponse>
        get() = _placeResponseLiveData

    private val _placeMarkerLiveData: MutableLiveData<Place> = MutableLiveData()
    val placeMarkerLiveData: LiveData<Place>
        get() = _placeMarkerLiveData

    private val _searchListLiveData: MutableLiveData<List<Any>> = MutableLiveData()
    val searchListLiveData: LiveData<List<Any>>
        get() = _searchListLiveData

    //사용자의 상태를 체크하는 liveData, 값에 따라서 백드롭의 타이틀 변경에 사용
    private val _userStatusLiveData: MutableLiveData<UserStatus> = MutableLiveData()
    val userStatusLiveData: LiveData<UserStatus>
        get() = _userStatusLiveData

    private val _markerList: MutableList<Marker> = arrayListOf()

    override fun onSaveFilterMarker(marker: Marker) {
        _markerList.add(marker)
    }

    override fun onRemoveFilterMarkers() {
        _markerList.forEach { marker ->
            marker.map = null

        }
    }

    override fun searchTrigger(view : View) {
        if(view is BackdropMotionLayout)
        {

        }
    }

    override fun onClickMarkerPlace(place: Place) {
        _placeMarkerLiveData.postValue(place)
    }

    override fun onClickMarkerHouse(house: MarkerInfo) {
        _markerLiveData.postValue(house)
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
            mapRepository.getPlace(
                currentMarkerInfo.latLng.latitude,
                currentMarkerInfo.latLng.longitude,
                RADIUS,
                category
            ).subscribe({ response ->
                val placeResponse = response.body()
                _placeResponseLiveData.postValue(placeResponse)
                _searchListLiveData.postValue(placeResponse?.placeList)
                _userStatusLiveData.postValue(
                    UserStatus(
                        StatusCode.SUCCESS_SEARCH_PLACE,
                        String.format(
                            view?.context!!.getString(R.string.info_success_search_place_text),
                            placeResponse?.placeList!![0].category,
                            placeResponse?.placeList.size
                        )
                    )
                )
            }, {
                _userStatusLiveData.postValue(UserStatus(StatusCode.FAILURE_SEARCH_PLACE, "검색에 실패했습니다."))
            })
        }
    }

    override fun onMapLongClick(point: PointF, latLng: LatLng) {
        _userStatusLiveData.postValue(UserStatus(StatusCode.SEARCH_HOUSE, "${latLng.latitude}, ${latLng.longitude}"))
        loadHousePrice(latLng)
    }

    override fun onMapReady(naverMap: NaverMap) {
        _mapStatusLiveData.postValue(naverMap)
        _userStatusLiveData.postValue(UserStatus(StatusCode.DEFAULT_SEARCH, ""))
    }

    private fun loadHousePrice(latLng: LatLng) = mapRepository
        .getAddress(latLng.latitude.toString(), latLng.longitude.toString(), "WGS84")
        .subscribe({ result ->
            val addressData = result.body()
            if (addressData?.metaData?.total_count!! > 0) {
                val addressName = addressData.documentData[0].addressMeta.addressName
                mapRepository.getHouseDetail(addressName)
                    .subscribe({ houseInfo ->
                        val houseResponse = houseInfo.body()
                        if (houseResponse?.addrStatusCode == StatusCode.RESULT_200.response
                            && houseResponse.houseStatusCode == StatusCode.RESULT_200.response
                        ) {
                            _markerLiveData.postValue(
                                MarkerInfo(
                                    latLng,
                                    houseResponse.houseList,
                                    StatusCode.RESULT_200
                                )
                            )
                            _userStatusLiveData.postValue(
                                UserStatus(StatusCode.SUCCESS_SEARCH_HOUSE, houseResponse.houseList[0].name)
                            )
                            val houseLastItemList = listOf(HouseInfo(houseResponse.houseList[houseResponse.houseList.size-1], addressName))
                            _searchListLiveData.postValue(houseLastItemList)

                        } else {
                            _userStatusLiveData.postValue(UserStatus(StatusCode.EMPTY_SEARCH_HOUSE, ""))
                            _searchListLiveData.postValue(listOf(EmptyInfo(addressName)))
                            _markerLiveData.postValue(MarkerInfo(latLng, emptyList(), StatusCode.RESULT_204))
                        }
                    }, {
                        //TODO: timeout 시간이 너무 길어서 의견 조율이 필요함.
                        _userStatusLiveData.postValue(
                            UserStatus(StatusCode.FAILURE_SEARCH_HOUSE, "")
                        )
                        _markerLiveData.postValue(MarkerInfo(latLng, emptyList(), StatusCode.RESULT_204))
                    })
            }
        }, {
            _userStatusLiveData.postValue(UserStatus(StatusCode.FAILURE_SEARCH_HOUSE, ""))
        })
}