package kr.co.connect.boostcamp.livewhere.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    @SerializedName("pnu") val pnuCode: String,//pnuCode
    @SerializedName("road_address_name") val roadAddr: String, // 도로명 주소
    @SerializedName("building_name") val name: String, // 건물 이름
    @SerializedName("address_name") val addr: String, // 지번 주소
    @SerializedName("x") val x: String, // 경도
    @SerializedName("y") val y: String // 위도
) : Parcelable