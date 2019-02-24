package kr.co.connect.boostcamp.livewhere.model

import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("address_name") val addrName: String, // 지번주소
    @SerializedName("category_group_name") val category: String,
    @SerializedName("distance") val distance: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("place_name") val placeName: String,
    @SerializedName("place_url") val place_url: String,
    @SerializedName("road_address_name") val roadAddr: String,
    @SerializedName("x") val x: String,
    @SerializedName("y") val y: String
)