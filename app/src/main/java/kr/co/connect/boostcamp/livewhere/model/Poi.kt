package kr.co.connect.boostcamp.livewhere.model

import com.google.gson.annotations.SerializedName

data class Poi(
    @SerializedName("name") val addressName: String,
    @SerializedName("upperAddrName") val upperAddrName: String,
    @SerializedName("middleAddrName") val middleAddrName: String,
    @SerializedName("lowerAddrName") val lowerAddrName: String,
    @SerializedName("frontLat") val latitude: String,
    @SerializedName("frontLon") val longitude: String
)