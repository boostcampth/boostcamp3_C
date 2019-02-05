package kr.co.connect.boostcamp.livewhere.ui.map

import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationSource
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons.BLUE
import com.naver.maps.map.widget.LocationButtonView
import com.naver.maps.map.widget.ZoomControlView
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.model.MarkerInfo
import kr.co.connect.boostcamp.livewhere.model.PlaceResponse
import kr.co.connect.boostcamp.livewhere.util.StatusCode


//맵 초기화 관련 onMakeNaverMapData
@BindingAdapter(value = ["onMakeNaverMapData", "bindViewModel"])
fun MapView.onMakeNaverMap(mapStatusLiveData: LiveData<NaverMap>, mapViewModel: MapViewModel) {
    Log.d("naverStatus", "make")
    if (mapStatusLiveData.value != null) {
        val naverMap: NaverMap = mapStatusLiveData.value!!
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)//building
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true)//대중교통
        naverMap.uiSettings.isCompassEnabled = true // 나침반
        naverMap.uiSettings.isLogoClickEnabled = false//로고 클릭 이벤트
        naverMap.locationTrackingMode = LocationTrackingMode.Face//위치추적모드
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.uiSettings.isLocationButtonEnabled = false//위치찾기 버튼 이벤트
        naverMap.uiSettings.isScaleBarEnabled = false
        naverMap.isIndoorEnabled = true//실내모드
        naverMap.uiSettings.isIndoorLevelPickerEnabled = true
        naverMap.uiSettings.isZoomControlEnabled = false//줌 버튼 이벤트
        naverMap.onMapLongClickListener = mapViewModel //맵클릭
        //naverMap.onMapClickListener = mapViewModel //맵롱클릭
    }
}


@BindingAdapter(value = ["onDrawMarker"])
fun MapView.OnDrawMarker(markerInfoLiveData: LiveData<MarkerInfo>) {
    val markerInfo = markerInfoLiveData.value
    if (markerInfo != null) {
        val latLang = markerInfo.latLng
        val houseList = markerInfo.houseList
        val statusCode = markerInfo.statusCode
        getMapAsync { naverMap ->
            val infoWindow = InfoWindow()
            val marker = Marker()
            var streetImgUrl = ""
            var title = ""
            if (statusCode == StatusCode.RESULT_200) {
                streetImgUrl = String.format(
                    BuildConfig.BaseGoogleUrl,
                    latLang.latitude,
                    latLang.longitude,
                    BuildConfig.GoogleApiKey
                )
                title = houseList[0].rentCase + ":" + houseList[0].deposite + "/" + houseList[0].fee
                marker.tag = houseList[0].name
            } else if (statusCode == StatusCode.RESULT_204) {
                streetImgUrl = ""
                title = context.resources.getString(R.string.title_detail_history_empty)
                marker.tag = ""
            }

            infoWindow.apply {
                position = LatLng(latLang.latitude, latLang.longitude)
                adapter = MapMarkerAdapter(
                    context,
                    parent,
                    streetImgUrl,
                    "",
                    title,
                    context.getString(R.string.all_find_house_text)
                )
                setOnClickListener {
                    Log.d("hello","on")
                    false
                }
                setOnTouchListener { v, event ->  false}
            }

            if (tag != null) {
                val tempMarker = tag as Marker
                tempMarker.map = null
                tempMarker.infoWindow?.close()
            }
            marker.apply {
                position = LatLng(latLang.latitude, latLang.longitude)
                setOnClickListener { _ ->
                    infoWindow.open(marker)
                    true
                }
                map = naverMap
            }
            tag = marker
        }
    }
}


@BindingAdapter(value = ["onPlaceDrawMarker"])
fun MapView.onPlaceDrawMarker(placeResponseLiveData: LiveData<PlaceResponse>) {
    val placeResponse = placeResponseLiveData.value
    if (placeResponse != null) {
        getMapAsync { naverMap ->
            placeResponse.placeList.forEach { place ->
                val marker = Marker()
                marker.position = LatLng(place.y.toDouble(), place.x.toDouble())
                val infoWindow = InfoWindow()
                infoWindow.adapter = MapMarkerAdapter(
                    context,
                    parent,
                    "",
                    place.place_url,
                    place.placeName,
                    context.getString(R.string.all_find_store_text)
                )
                marker.setOnClickListener {
                    infoWindow.open(marker)
                    true
                }
                marker.apply {
                    width = 50
                    height = 80
                    subCaptionText = place.placeName
                    icon = BLUE
                    /*when (place.category) {
                        "대형마트", "편의점" -> icon = BLUE
                        "어린이집", "유치원", "학교" -> icon = YELLOW
                        "음식점" -> icon = LIGHTBLUE
                        "카페" -> icon = PINK
                        "병원" -> icon = GREEN
                    }*/
                    map = naverMap
                }
            }
        }
    }
}

//zoom button 관련 onMakeNaverMapData
@BindingAdapter(value = ["onMakeNaverMapData"])
fun ZoomControlView.setOnClick(mapStatusLiveData: LiveData<NaverMap>) {
    map = mapStatusLiveData.value
    if (map != null) {
        map!!.uiSettings.isZoomControlEnabled = true
    }
}

//위치 찾기 관련 onMakeNaverMapData
@BindingAdapter(value = ["onMakeNaverMapData", "onLocationSource"])
fun LocationButtonView.setOnClick(mapStatusLiveData: LiveData<NaverMap>, locationSource: LocationSource) {
    map = mapStatusLiveData.value
    if (map != null) {
        map!!.locationSource = locationSource
        map!!.locationSource = mapStatusLiveData.value!!.locationSource
    }
}

@BindingAdapter(value = ["triggerBackdrop", "triggerFloatingButton", "triggerOnClick"])
fun FloatingActionButton.setTriggerBackdrop(
    backdropML: MotionLayout,
    filterML: MotionLayout,
    mapViewModel: MapViewModel
) {
    setOnClickListener { view ->
        if (backdropML.currentState == R.layout.motion_01_map_backdrop_end) {
            backdropML.transitionToStart()
        } else {
            filterML.transitionToStart()
        }
        mapViewModel.onClick(view)
    }
}

@BindingAdapter(value = ["triggerFloatingButton"])
fun MotionLayout.setTriggerFB(filterML: MotionLayout) {
    this.setTransitionListener(object : MotionLayout.TransitionListener {
        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

        override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {
            if (currentId == R.layout.motion_01_map_backdrop_start) {
                filterML.transitionToStart()
            } else if (currentId == R.layout.motion_01_map_backdrop_end) {
                filterML.transitionToStart()
            }
        }
    })
}