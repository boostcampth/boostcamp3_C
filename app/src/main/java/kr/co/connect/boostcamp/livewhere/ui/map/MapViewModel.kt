package kr.co.connect.boostcamp.livewhere.ui.map

import android.graphics.PointF
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.model.MarkerInfo
import kr.co.connect.boostcamp.livewhere.model.PlaceResponse
import kr.co.connect.boostcamp.livewhere.repository.MapRepositoryImpl
import kr.co.connect.boostcamp.livewhere.util.MapUtilImpl
import kr.co.connect.boostcamp.livewhere.util.StatusCode

class MapViewModel(val mapUtilImpl: MapUtilImpl, val mapRepository: MapRepositoryImpl) : ViewModel(),
    NaverMap.OnMapLongClickListener, OnMapReadyCallback, View.OnClickListener {
    private val _markerLiveData: MutableLiveData<MarkerInfo> = MutableLiveData()
    val markerLiveData: LiveData<MarkerInfo>
        get() = _markerLiveData

    private val _mapStatusLiveData: MutableLiveData<NaverMap> = MutableLiveData()
    val mapStatusLiveData: LiveData<NaverMap>
        get() = _mapStatusLiveData

    private val _placeResponseLiveData: MutableLiveData<PlaceResponse> = MutableLiveData()
    val placeResponseLiveData: LiveData<PlaceResponse>
        get() = _placeResponseLiveData

    override fun onClick(view: View?) {
        val currentMarkerInfo = markerLiveData.value
        var category = ""
        when {
            view?.id == R.id.fab_filter_cafe -> {
                category = "CE7"
            }
            view?.id == R.id.fab_filter_food -> {
                category = "FD6"
            }
            view?.id == R.id.fab_filter_hospital -> {
                category = "HP8"
            }
            view?.id == R.id.fab_filter_school -> {
                category = "PS3,SC4"
            }
            view?.id == R.id.fab_filter_store -> {
                category = "MT1,CS2"
            }
        }

        if (currentMarkerInfo != null) {
            mapRepository.getPlace(
                currentMarkerInfo.latLng.latitude,
                currentMarkerInfo.latLng.longitude,
                1000,
                category
            ).subscribe({ response ->
                val placeResponse = response.body()
                _placeResponseLiveData.postValue(placeResponse)
            }, {})
        }
    }

    override fun onMapLongClick(point: PointF, latLng: LatLng) {
        loadHousePrice(latLng)
    }

    override fun onMapReady(naverMap: NaverMap) {
        _mapStatusLiveData.postValue(naverMap)
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
                        } else {
                            _markerLiveData.postValue(MarkerInfo(latLng, emptyList(), StatusCode.RESULT_204))
                        }
                    }, {
                        //TODO: timeout 시간이 너무 길어서 의견 조율이 필요함.
                        _markerLiveData.postValue(MarkerInfo(latLng, emptyList(), StatusCode.RESULT_204))
                    })
            }
        }, {

        })
}