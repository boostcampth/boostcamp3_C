package kr.co.connect.boostcamp.livewhere.model

import com.google.gson.annotations.SerializedName

data class KakaoAddress(
    @SerializedName("address_name") val addressName: String,
    @SerializedName("region_1depth_name") val regionDepthOne: String,
    @SerializedName("region_2depth_name") val regionDepthTwo: String,
    @SerializedName("region_3depth_name") val regionDepthThree: String
)