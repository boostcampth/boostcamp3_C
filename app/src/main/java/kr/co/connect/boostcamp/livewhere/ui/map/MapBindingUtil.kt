package kr.co.connect.boostcamp.livewhere.ui.map

import android.graphics.drawable.Drawable
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.CircleOverlay
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons.*
import com.naver.maps.map.widget.LocationButtonView
import com.naver.maps.map.widget.ScaleBarView
import com.naver.maps.map.widget.ZoomControlView
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.model.*
import kr.co.connect.boostcamp.livewhere.ui.map.adapter.MapMarkerAdapter
import kr.co.connect.boostcamp.livewhere.ui.map.adapter.MapSearchRVAdapter
import kr.co.connect.boostcamp.livewhere.ui.map.view.BackdropMotionLayout
import kr.co.connect.boostcamp.livewhere.util.RADIUS
import kr.co.connect.boostcamp.livewhere.util.StatusCode


//맵 초기화 관련 onMakeNaverMapData
@BindingAdapter(value = ["onMakeNaverMapData", "bindViewModel"])
fun MapView.onMakeNaverMap(mapStatusLiveData: LiveData<NaverMap>, mapViewModel: MapViewModel) {
    if (mapStatusLiveData.value != null) {
        val naverMap: NaverMap = mapStatusLiveData.value!!
        naverMap.apply {
            symbolScale = 0.5f
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
                isScaleBarEnabled = false//스케일바 사용
                isIndoorLevelPickerEnabled = false//층수
                isZoomControlEnabled = false//줌 버튼 이벤트
            }
        }
    }
}

@BindingAdapter(value = ["onDrawHouse", "onClickHouseStreetView"])
fun ImageView.onDrawHouse(markerInfoLiveData: LiveData<MarkerInfo>, mapViewModel: MapViewModel) {
    val markerInfo = markerInfoLiveData.value
    if (markerInfo != null) {
        val latLang = markerInfo.latLng
        mapViewModel.onMoveCameraPosition(latLang,14.0)
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
                    mapViewModel.onClickMapImageView(this@onDrawHouse, markerInfoLiveData)
                    visibility = VISIBLE
                    return false
                }
            })
            .into(this)
    }
}

@BindingAdapter(value = ["onDrawPlace", "onClickPlaceStreetView"])
fun ImageView.onDrawPlace(placeMarkerLiveData: LiveData<Place>, mapViewModel: MapViewModel) {
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
                    mapViewModel.onClickMapImageView(this@onDrawPlace, placeMarkerLiveData)
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
        val statusCode = markerInfo.statusCode
        getMapAsync { naverMap ->
            mapViewModel.onRemoveFilterMarker()//주변 필터 삭제
            mapViewModel.onRemoveCircleOverlay()//오버레이 삭제
            mapViewModel.onClickMarkerHouse(markerInfo)//매물 이미지 추가
            val marker = Marker()
            if (statusCode == StatusCode.RESULT_200) {
                marker.tag = markerInfo
            } else if (statusCode == StatusCode.RESULT_204) {
                marker.tag = markerInfo
            }
            if (tag != null) {
                val tempMarker = tag as Marker
                tempMarker.apply {
                    map = null
                    infoWindow?.close()
                }
            }
            marker.apply {
                position = latLang
                setOnClickListener {
                    mapViewModel.onLoadBuildingList(listOf(markerInfo), rootView)//현재 매물 리스트에 반영
                    mapViewModel.onClickMarkerHouse(markerInfo)//매물 이미지 추가
                    true
                }
                map = naverMap
            }
            val mInfoWindow = InfoWindow()
            mInfoWindow.apply {
                adapter = MapMarkerAdapter(context, mapViewModel.userStatusLiveData.value?.content!!)
                open(marker)
            }
            tag = marker //해당 마커를 닫기 위해서 tag에 marker 값을 저장
            mapViewModel.onMoveCameraPosition(latLang, 17.0)//cameraposition 이동
        }
    }
}

@BindingAdapter(value = ["onCameraUpdate"])
fun MapView.moveCameraPosition(cameraPositionLatLngLiveData: LiveData<CameraPositionInfo>) {
    getMapAsync { naverMap ->
        val cameraPositionInfo = cameraPositionLatLngLiveData.value
        if (cameraPositionInfo != null) {
            val latLng = cameraPositionInfo.latLng
            val zoom = cameraPositionInfo.zoom
            val cameraUpdate = CameraUpdate.toCameraPosition(CameraPosition(latLng, zoom))
                .animate(CameraAnimation.Linear, 1000)
            naverMap.moveCamera(cameraUpdate)
        }
    }
}

@BindingAdapter(value = ["onPlaceDrawMarker", "onClickPlaceMarker"])
fun MapView.onPlaceDrawMarker(placeResponseLiveData: LiveData<PlaceResponse>, mapViewModel: MapViewModel) {
    val placeResponse = placeResponseLiveData.value
    if (placeResponse != null) {
        val placeMarkerList: MutableList<Marker> = arrayListOf()
        getMapAsync { naverMap ->
            mapViewModel.onRemoveFilterMarker()//주변 필터 삭제
            mapViewModel.onRemoveCircleOverlay()
            placeResponse.placeList.forEach { place ->
                val marker = Marker()
                marker.apply {
                    position = LatLng(place.y.toDouble(), place.x.toDouble())
                    width = 50
                    height = 80
                    subCaptionText = place.placeName
                    icon = when (place.category) {
                        "대형마트", "편의점" -> BLUE
                        "어린이집,유치원", "학교" -> YELLOW
                        "음식점" -> LIGHTBLUE
                        "카페" -> PINK
                        "병원" -> GREEN
                        else -> BLUE
                    }
                    setOnClickListener {
                        val placeIndex = placeResponse.placeList.indexOf(place)
                        val tempPlace = placeResponse.placeList[placeIndex]
                        val mutablePlaceList = placeResponse.placeList.toMutableList()
                        mutablePlaceList[placeIndex] = mutablePlaceList[0]
                        mutablePlaceList[0] = tempPlace
                        mapViewModel.onLoadBuildingList(mutablePlaceList, rootView)//현재 장소 리스트에 반영
                        mapViewModel.onClickMarkerPlace(place)//현재 상권의 이미지를 출력
                        mapViewModel.onRemoveInfoWindow()
                        val latLng = LatLng(place.y.toDouble(), place.x.toDouble())
                        val mInfoWindow = mapViewModel.makePlaceDrawInfoWindow(
                            this@onPlaceDrawMarker,
                            mapViewModel,
                            latLng,
                            place.placeName
                        )
                        mInfoWindow.open(marker)
                        true
                    }
                    map = naverMap
                    placeMarkerList.add(marker)
                }
            }
            val overlay = CircleOverlay()
            overlay.apply {
                center = LatLng(
                    mapViewModel.markerLiveData.value?.latLng?.latitude!!,
                    mapViewModel.markerLiveData.value?.latLng?.longitude!!
                )//중앙 위치
                radius = RADIUS.toDouble()//반경
                color = 0x4000FF00.toInt()//색깔
                outlineColor = 0xC000c148.toInt()
                outlineWidth = 10
                map = naverMap//맵셋팅
            }
            mapViewModel.onSaveCircleOverlay(overlay)
            mapViewModel.onSaveFilterMarker(placeMarkerList)//마커 값 저장
        }
    }
}


@BindingAdapter(value = ["onRemovePlaceMarker"])
fun MapView.onRemovePlaceMarker(tempInfoWindowLiveData: LiveData<InfoWindow>) {
    getMapAsync {
        val tempWindow = tempInfoWindowLiveData.value
        if (tempWindow != null) {
            tempWindow.map = null
            tempWindow.close()
        }
    }
}

@BindingAdapter(value = ["onRemovePlaceOverlay"])
fun MapView.onRemovePlaceOverlay(tempOverlayLiveData: LiveData<CircleOverlay>) {
    val tempOverlay = tempOverlayLiveData.value
    tempOverlay?.map = null
}

@BindingAdapter(value = ["onRemovePlaceDrawMarker"])
fun MapView.onRemovePlaceDrawMarker(removePlaceMarkersLiveData: LiveData<MutableList<Marker>>) {
    val removeMarkerList = removePlaceMarkersLiveData.value
    removeMarkerList?.forEach { marker ->
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

@BindingAdapter(value = ["onMakeNaverMapData"])
fun ScaleBarView.onStartScale(mapStatusLiveData: LiveData<NaverMap>) {
    map = mapStatusLiveData.value
    if (map != null) {
        map!!.uiSettings.isScaleBarEnabled = true
    }
}

//위치 찾기 관련 onMakeNaverMapData
@BindingAdapter(value = ["onMakeNaverMapData", "onLocationSource"])
fun LocationButtonView.setOnClick(mapStatusLiveData: LiveData<NaverMap>, locationSource: LocationSource) {
    map = mapStatusLiveData.value
    if (map != null) {
        map!!.locationSource = locationSource
    }
}

@BindingAdapter(value = ["triggerBackdrop", "triggerFloatingButton", "triggerOnClick"])
fun FloatingActionButton.setTriggerBackdrop(
    backdropML: MotionLayout,
    filterML: MotionLayout,
    mapViewModel: MapViewModel
) {
    setOnClickListener { view ->
        val latLng = mapViewModel.markerLiveData.value?.latLng
        if (latLng != null) {
            mapViewModel.onMoveCameraPosition(latLng, 14.0)
        }
        mapViewModel.onClick(view)//필터 버튼 클릭시에 일어나는 행위
        filterML.transitionToStart()//필터를 닫음
    }
}

@BindingAdapter(value = ["bindData"])
fun RecyclerView.setBindPlaceData(bindPlaceLiveData: LiveData<List<Any>>) {
    if (bindPlaceLiveData.value != null) {
        val bindList = bindPlaceLiveData.value
        if (adapter == null) {
            apply {
                adapter?.notifyItemRangeInserted(0, bindList!!.size)
                (adapter as MapSearchRVAdapter).setItemChange(bindList!!)
            }
        } else {
            apply {
                adapter?.notifyItemRangeRemoved(0, adapter?.itemCount!!)
                (adapter as MapSearchRVAdapter).setItemChange(bindList!!)
                adapter?.notifyItemRangeInserted(0, bindList.size)
            }
        }
    } else {
        val emptyList = arrayListOf<Any>()
        (adapter as MapSearchRVAdapter).setItemChange(emptyList)
        adapter?.notifyItemRangeInserted(0, emptyList.size)
    }
}

@BindingAdapter(value=["onMoveFirstStepLiveData"])
fun RecyclerView.moveFirstStep(onMoveFirstStepLiveData:LiveData<Boolean>){
    if(onMoveFirstStepLiveData.value != null && onMoveFirstStepLiveData.value==true)
    {
       smoothScrollToPosition(0)
    }
}

@BindingAdapter(value = ["searchLiveData"])
fun BackdropMotionLayout.changeSearchHeight(searchListLiveData: LiveData<List<Any>>) {
    if (searchListLiveData.value != null) {
        if (currentState == R.layout.motion_01_map_backdrop_start) {
            transitionToEnd()
        }
    }
}

@BindingAdapter(value = ["onStatusTitleEvent"])
fun TextView.setStatusTextView(userStatusLiveData: LiveData<UserStatus>) {
    val statusCode = userStatusLiveData.value?.statusCode
    text = when (statusCode) {
        StatusCode.DEFAULT_SEARCH -> context.getString(R.string.map_init_message)
        StatusCode.BEFORE_SEARCH_PLACE -> context.getString(R.string.info_before_search_place_text)
        StatusCode.SEARCH_PLACE -> userStatusLiveData.value?.content
        StatusCode.SEARCH_HOUSE -> userStatusLiveData.value?.content
        StatusCode.EMPTY_SEARCH_HOUSE -> context.getString(R.string.info_empty_search_house_text)
        StatusCode.EMPTY_SEARCH_PLACE -> context.getString(R.string.info_empty_search_place_text)
        StatusCode.EMPTY_HOUSE_TARGET -> context.getString(R.string.info_empty_house_target)
        StatusCode.FAILURE_SEARCH_PLACE -> context.getString(R.string.info_failure_search)
        StatusCode.FAILURE_SEARCH_HOUSE -> context.getString(R.string.info_failure_search)
        StatusCode.SUCCESS_SEARCH_PLACE -> userStatusLiveData.value?.content
        StatusCode.SUCCESS_SEARCH_HOUSE -> userStatusLiveData.value?.content
        else -> ""
    }
}

@BindingAdapter(value = ["onStatusTitleEvent", "onBehavior"])
fun setStatusTextView(toolbar: Toolbar, userStatusLiveData: LiveData<UserStatus>, mapViewModel: MapViewModel) {
    val statusCode = userStatusLiveData.value?.statusCode
    val context = toolbar.context
    when (statusCode) {
        StatusCode.SEARCH_PLACE -> mapViewModel.startObservable(
            context.getString(R.string.info_before_search_place_text),
            toolbar
        )
        StatusCode.SEARCH_HOUSE -> mapViewModel.startObservable(
            context.getString(R.string.info_before_search_place_text),
            toolbar
        )
        else -> mapViewModel.stopObservable()
    }

    toolbar.title = when (statusCode) {
        StatusCode.DEFAULT_SEARCH -> context.getString(R.string.map_init_message)
        StatusCode.BEFORE_SEARCH_PLACE -> context.getString(R.string.info_before_search_place_text)
        StatusCode.EMPTY_SEARCH_HOUSE -> context.getString(R.string.info_empty_search_house_text)
        StatusCode.EMPTY_SEARCH_PLACE -> context.getString(R.string.info_empty_search_place_text)
        StatusCode.EMPTY_HOUSE_TARGET -> context.getString(R.string.info_empty_house_target)
        StatusCode.FAILURE_SEARCH_PLACE -> context.getString(R.string.info_failure_search)
        StatusCode.FAILURE_SEARCH_HOUSE -> context.getString(R.string.info_failure_search)
        StatusCode.SUCCESS_SEARCH_PLACE -> userStatusLiveData.value?.content
        StatusCode.SUCCESS_SEARCH_HOUSE -> userStatusLiveData.value?.content
        else -> ""
    }


}

@BindingAdapter(value = ["onClickTriggerBackDrop"])
fun ImageView.onClickTriggerBackDrop(backdropML: MotionLayout) {
    setOnClickListener {
        when {
            backdropML.currentState == R.layout.motion_01_map_backdrop_start -> backdropML.transitionToEnd()
            backdropML.currentState == R.layout.motion_01_map_backdrop_middle -> {
                backdropML.transitionToState(R.layout.motion_01_map_backdrop_end)
                backdropML.transitionToStart()
            }
            backdropML.currentState == R.layout.motion_01_map_backdrop_end -> backdropML.transitionToStart()
        }
    }
}

@BindingAdapter(value = ["isHomeClick"])
fun Toolbar.onInitToolbar(isHomeClick: Boolean) {
    setNavigationOnClickListener {
        if (isHomeClick) {
            (context as MapActivity).finish()
        }
    }
}

@BindingAdapter(value = ["onTitleToolbar"])
fun Toolbar.onTitleToolbar(markerLiveData: LiveData<MarkerInfo>) {
    if (markerLiveData.value != null) {
        title = markerLiveData.value!!.address.addr
    }
}

@BindingAdapter(value = ["onMoveFirstBackDrop"])
fun BackdropMotionLayout.onMoveFirstBackDrop(onMoveFirstStepLiveData: LiveData<Boolean>) {
    if (onMoveFirstStepLiveData.value != null && onMoveFirstStepLiveData.value == true) {
        if (currentState == R.layout.motion_01_map_backdrop_end) {
            transitionToStart()
        }
    }
}

