package kr.co.connect.boostcamp.livewhere.model

import com.google.gson.annotations.SerializedName

data class KakaoPlaceResponse(
    @SerializedName("address_name") val addressName:String,
    @SerializedName("address") val addressList:List<KakaoAddress>
)