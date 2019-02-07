package kr.co.connect.boostcamp.livewhere.model

import com.naver.maps.geometry.LatLng
import kr.co.connect.boostcamp.livewhere.util.StatusCode

data class MarkerInfo(
    val latLng: LatLng,
    val houseList: List<House>,
    val statusCode: StatusCode)