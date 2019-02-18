package kr.co.connect.boostcamp.livewhere.ui.map

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.InfoWindow
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.model.MarkerInfo
import kr.co.connect.boostcamp.livewhere.model.Place

@BindingAdapter(value = ["onWebClickListener", "onClickWebSite"])
fun LinearLayout.setOnWebClickListener(placeUrl: String, mapViewModel: MapViewModel) {
    setOnClickListener {
        mapViewModel.onLaunchUrl(context, placeUrl)
    }
}

@BindingAdapter(value = ["onCurrentPlace", "onPlaceItemClickListener"])
fun LinearLayout.setOnPlaceItemClickListener(currentPlace: Place, mapViewModel: MapViewModel) {
    setOnClickListener {
        val tempInfoWindow = mapViewModel.tempInfoWindowLiveData.value
        val placeResponse = mapViewModel.placeResponseLiveData.value
        val currentIndex = placeResponse?.placeList?.indexOf(currentPlace)
        val marker = mapViewModel.savePlaceMarkersLiveData.value!![currentIndex!!]
        val targetPlace = placeResponse.placeList[currentIndex]
        val latLng = LatLng(targetPlace.y.toDouble(), targetPlace.x.toDouble())
        val infoWindow = mapViewModel.makePlaceDrawInfoWindow(this, mapViewModel, latLng, targetPlace.placeName)

        val mutablePlaceList = placeResponse.placeList.toMutableList()
        if (tempInfoWindow != null) {
            tempInfoWindow.map = null
            tempInfoWindow.close()
        }
        mapViewModel.onRemoveInfoWindow()
        val tempPlace = placeResponse.placeList[currentIndex]
        mutablePlaceList[currentIndex] = mutablePlaceList[0]
        mutablePlaceList[0] = tempPlace
        mapViewModel.onMoveFirstStep(true)
        mapViewModel.onLoadBuildingList(mutablePlaceList, rootView)//현재 장소 리스트에 반영
        mapViewModel.onSaveInfoWindow(infoWindow)
        infoWindow.open(marker)
    }
}


@BindingAdapter(value = ["onCurrentHouse", "onHouseItemClickListener"])
fun LinearLayout.setOnPlaceItemClickListener(markerInfo: MarkerInfo, mapViewModel: MapViewModel) {
    setOnClickListener {
        val latLng = markerInfo.latLng
        mapViewModel.onLoadBuildingList(listOf(markerInfo), rootView)//현재 매물 리스트에 반영
        mapViewModel.onClickMarkerHouse(markerInfo)//매물 이미지 추가
        val mInfoWindow = InfoWindow()
        mapViewModel.onMoveCameraPosition(latLng, 14.0)
    }
}

@BindingAdapter(value = ["onHomeClickListener", "onMarkerInfo"])
fun LinearLayout.startActivityWithIntent(mapViewModel: MapViewModel, markerInfo: MarkerInfo) {
    mapViewModel.onStartDetailActivity(context, markerInfo)
    setOnClickListener {
        mapViewModel.onNextStartActivity(this, markerInfo)
    }
}

@BindingAdapter(value = ["onText"])
fun TextView.onTextView(content: String) {
    text = content.replace("\n", " ")
}

@BindingAdapter(value = ["onDrawCategory"])
fun ImageView.setOnDrawCategory(category: String) {
    when (category) {
        "학교", "어린이집,유치원" -> setBackgroundResource(R.drawable.ic_school)
        "편의점", "대형마트" -> setBackgroundResource(R.drawable.ic_mall)
        "음식점" -> setBackgroundResource(R.drawable.ic_signboard)
        "카페" -> setBackgroundResource(R.drawable.ic_cafe)
        "병원" -> setBackgroundResource(R.drawable.ic_hospital)
    }
}