package kr.co.connect.boostcamp.livewhere.model

import android.os.Parcelable
import com.naver.maps.geometry.LatLng
import kotlinx.android.parcel.Parcelize
import kr.co.connect.boostcamp.livewhere.util.StatusCode

@Parcelize
data class MarkerInfo(
    val address: Address,
    val latLng: LatLng,
    val houseList: List<House>,
    val statusCode: StatusCode
) : Parcelable