package kr.co.connect.boostcamp.livewhere.ui.map

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationSource
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.CircleOverlay
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons.*
import com.naver.maps.map.widget.LocationButtonView
import com.naver.maps.map.widget.ZoomControlView
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.model.MarkerInfo
import kr.co.connect.boostcamp.livewhere.model.Place
import kr.co.connect.boostcamp.livewhere.model.PlaceResponse
import kr.co.connect.boostcamp.livewhere.model.UserStatus
import kr.co.connect.boostcamp.livewhere.util.RADIUS
import kr.co.connect.boostcamp.livewhere.util.StatusCode


//맵 초기화 관련 onMakeNaverMapData
@BindingAdapter(value = ["onMakeNaverMapData", "bindViewModel"])
fun MapView.onMakeNaverMap(mapStatusLiveData: LiveData<NaverMap>, mapViewModel: MapViewModel) {
    if (mapStatusLiveData.value != null) {
        val naverMap: NaverMap = mapStatusLiveData.value!!
        naverMap.apply {
            onMapLongClickListener = mapViewModel //맵롱클릭
            isIndoorEnabled = true//실내모드
            setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)//building
            setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true)//대중교통
            locationTrackingMode.apply {
                LocationTrackingMode.Face//위치추적모드
                LocationTrackingMode.Follow
            }
            uiSettings.apply {
                isCompassEnabled = true // 나침반
                isLogoClickEnabled = false//로고 클릭 이벤트
                isLocationButtonEnabled = false//위치찾기 버튼 이벤트
                isScaleBarEnabled = false
                isIndoorLevelPickerEnabled = true
                isZoomControlEnabled = false//줌 버튼 이벤트
            }
        }
    }
}

@BindingAdapter(value = ["onDrawHouse"])
fun ImageView.onDrawHouse(markerInfoLiveData: LiveData<MarkerInfo>) {
    val markerInfo = markerInfoLiveData.value
    if (markerInfo != null) {
        val latLang = markerInfo.latLng
        val streetImgUrl = String.format(
            BuildConfig.BaseGoogleUrl,
            latLang.latitude,
            latLang.longitude,
            BuildConfig.GoogleApiKey
        )
        Glide.with(this)
            .load(streetImgUrl)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    visibility = VISIBLE
                    return false
                }
            })
            .into(this)
    }
}

@BindingAdapter(value = ["onDrawPlace"])
fun ImageView.onDrawPlace(placeMarkerLiveData: LiveData<Place>) {
    val placeLiveData = placeMarkerLiveData.value
    if (placeLiveData != null) {
        val streetImgUrl = String.format(
            BuildConfig.BaseGoogleUrl,
            placeLiveData.y,
            placeLiveData.x,
            BuildConfig.GoogleApiKey
        )
        Glide.with(this)
            .load(streetImgUrl)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    visibility = VISIBLE
                    return false
                }
            })
            .into(this)
    }


}

@BindingAdapter(value = ["onHouseDrawMarker", "onClickHouseMarker"])
fun MapView.onHouseDrawMarker(markerInfoLiveData: LiveData<MarkerInfo>, mapViewModel: MapViewModel) {
    val markerInfo = markerInfoLiveData.value
    if (markerInfo != null) {
        val latLang = markerInfo.latLng
        val houseList = markerInfo.houseList
        val statusCode = markerInfo.statusCode
        getMapAsync { naverMap ->
            val marker = Marker()
            if (statusCode == StatusCode.RESULT_200) {
                marker.tag = houseList[0].name
            } else if (statusCode == StatusCode.RESULT_204) {
                marker.tag = ""
            }

            if (tag != null) {
                val tempMarker = tag as Marker
                tempMarker.map = null
                tempMarker.infoWindow?.close()
            }
            marker.apply {
                position = LatLng(latLang.latitude, latLang.longitude)
                map = naverMap
                setOnClickListener {
                    mapViewModel.onClickMarkerHouse(markerInfo)
                    true
                }
            }
            tag = marker
        }
    }
}

@BindingAdapter(value = ["onPlaceDrawMarker", "onClickPlaceMarker"])
fun MapView.onPlaceDrawMarker(placeResponseLiveData: LiveData<PlaceResponse>, mapViewModel: MapViewModel) {
    val placeResponse = placeResponseLiveData.value
    if (placeResponse != null) {
        val placeMarkerList : MutableList<Marker> = arrayListOf()
        getMapAsync { naverMap ->
            val circle = CircleOverlay()
            val latLng = mapViewModel.markerLiveData.value?.latLng
            mapViewModel.onRemoveFilterMarker()//마커 삭제
            circle.apply {
                center = LatLng(latLng?.latitude!!, latLng.longitude)//중앙 위치
                radius = RADIUS.toDouble()//반경
                circle.color = 0x5000FF00.toInt()//색깔
                map = naverMap//맵셋팅

            }
            placeResponse.placeList.forEach { place ->
                val marker = Marker()
                val infoWindow = InfoWindow()
                infoWindow.adapter = MapMarkerAdapter(context, place.placeName)
                marker.apply {
                    position = LatLng(place.y.toDouble(), place.x.toDouble())
                    width = 50
                    height = 80
                    subCaptionText = place.placeName
                    icon = when (place.category) {
                        "대형마트", "편의점" -> BLUE
                        "어린이집", "유치원", "학교" -> YELLOW
                        "음식점" -> LIGHTBLUE
                        "카페" -> PINK
                        "병원" -> GREEN
                        else -> BLUE
                    }
                    map = naverMap
                    setOnClickListener {
                        mapViewModel.onClickMarkerPlace(place)
                        infoWindow.open(marker)
                        infoWindow.invalidate()
                        true
                    }
                    placeMarkerList.add(marker)
                }
            }
            mapViewModel.onSaveFilterMarker(placeMarkerList)//마커 값 저장
        }
    }
}

@BindingAdapter(value =["onRemovePlaceDrawMarker"])
fun MapView.onRemovePlaceDrawMarker(removePlaceMarkersLiveData: LiveData<MutableList<Marker>>) {
    val removeMarkerList = removePlaceMarkersLiveData.value
    removeMarkerList?.forEach { marker->
        marker.map = null
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
    setTransitionListener(object : MotionLayout.TransitionListener {
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

@BindingAdapter(value = ["bindData"])
fun RecyclerView.setBindPlaceData(bindPlaceLiveData: LiveData<List<Any>>) {
    if (bindPlaceLiveData.value != null) {
        val bindList = bindPlaceLiveData.value
        if (adapter == null) {
            Log.d("first", bindList?.size.toString())
            apply {
                layoutManager = LinearLayoutManager(context)
                adapter = MapSearchRVAdapter(bindList)
                adapter?.notifyItemRangeInserted(0, bindList?.size!!)
            }
        } else {
            Log.d("second", bindList?.size.toString())
            apply {
                adapter?.notifyItemRangeRemoved(0, adapter?.itemCount!!)
                adapter = MapSearchRVAdapter(bindList)
            }
        }
    } else {
        val emptyList = arrayListOf<Any>()
        layoutManager = LinearLayoutManager(context)
        adapter = MapSearchRVAdapter(emptyList)
        adapter?.notifyItemRangeInserted(0, emptyList.size)
    }
}

@BindingAdapter(value = ["triggerSearchHeight", "searchLiveData"])
fun BackdropMotionLayout.changeSearchHeight(mapViewModel: MapViewModel, searchListLiveData: LiveData<List<Any>>) {
    if (searchListLiveData.value != null) {
        mapViewModel.searchTrigger(this)
    }
}

@BindingAdapter(value = ["onStatusTitleEvent"])
fun TextView.setStatusTextView(userStatusLiveData: LiveData<UserStatus>) {
    val statusCode = userStatusLiveData.value?.statusCode
    text = when (statusCode) {
        StatusCode.DEFAULT_SEARCH -> context.getString(R.string.home_bookmark)
        StatusCode.BEFORE_SEARCH_PLACE -> context.getString(R.string.info_before_search_place_text)
        StatusCode.SEARCH_PLACE -> userStatusLiveData.value?.content
        StatusCode.SEARCH_HOUSE -> userStatusLiveData.value?.content
        StatusCode.EMPTY_SEARCH_HOUSE -> context.getString(R.string.info_empty_search_house_text)
        StatusCode.FAILURE_SEARCH_PLACE -> context.getString(R.string.info_failure_search)
        StatusCode.FAILURE_SEARCH_HOUSE -> context.getString(R.string.info_failure_search)
        StatusCode.SUCCESS_SEARCH_PLACE -> userStatusLiveData.value?.content
        StatusCode.SUCCESS_SEARCH_HOUSE -> userStatusLiveData.value?.content
        else -> ""
    }
}