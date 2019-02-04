package kr.co.connect.boostcamp.livewhere.ui.map

import android.graphics.PointF
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import kr.co.connect.boostcamp.livewhere.model.MarkerInfo
import kr.co.connect.boostcamp.livewhere.repository.MapRepositoryImpl
import kr.co.connect.boostcamp.livewhere.util.MapUtilImpl
import kr.co.connect.boostcamp.livewhere.util.StatusCode

class MapViewModel(val mapUtilImpl: MapUtilImpl, val mapRepository: MapRepositoryImpl) : ViewModel(),
    NaverMap.OnMapLongClickListener, NaverMap.OnMapClickListener, OnMapReadyCallback {

    private val _markerLiveData: MutableLiveData<MarkerInfo> = MutableLiveData()
    val markerLiveData: LiveData<MarkerInfo>
        get() = _markerLiveData

    private val _mapStatusLiveData: MutableLiveData<NaverMap> = MutableLiveData()
    val mapStatusLiveData: LiveData<NaverMap>
        get() = _mapStatusLiveData


    override fun onMapLongClick(point: PointF, latLng: LatLng) {
        loadHousePrice(latLng)
    }

    override fun onMapClick(point: PointF, latLng: LatLng) {
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
                            && houseResponse.houseStatusCode == StatusCode.RESULT_200.response)
                        {
                            _markerLiveData.postValue(MarkerInfo(latLng,houseResponse.houseList,StatusCode.RESULT_200))
                        } else {
                            _markerLiveData.postValue(MarkerInfo(latLng, emptyList(), StatusCode.RESULT_204))
                        }
                    }, {
                        //TODO: timeout 시간이 너무 길어서 의견 조율이 필요함.
                        _markerLiveData.postValue(MarkerInfo(latLng, emptyList(), StatusCode.RESULT_204))
                    })
            } }, {

        })
}