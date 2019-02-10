package kr.co.connect.boostcamp.livewhere.ui.map

import com.naver.maps.map.overlay.CircleOverlay
import com.naver.maps.map.overlay.Marker
import kr.co.connect.boostcamp.livewhere.model.MarkerInfo
import kr.co.connect.boostcamp.livewhere.model.Place

interface OnMapHistoryListener{
    fun onClickMarkerPlace(place: Place)
    fun onClickMarkerHouse(house: MarkerInfo)
    fun onSaveFilterMarker(markerList:MutableList<Marker>)
    fun onRemoveFilterMarker()
    fun onSaveCircleOverlay(circleOverlay: CircleOverlay)
    fun onRemoveCircleOverlay()
    fun onDrawCircleOverlay(currentOverlay: CircleOverlay)
}