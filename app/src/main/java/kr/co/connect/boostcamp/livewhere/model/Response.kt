package kr.co.connect.boostcamp.livewhere.model

import com.google.gson.annotations.SerializedName

data class Response (
    @SerializedName("documents") val documents: List<KakaoPlaceResponse>
)