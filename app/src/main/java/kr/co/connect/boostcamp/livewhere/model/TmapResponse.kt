package kr.co.connect.boostcamp.livewhere.model

import com.google.gson.annotations.SerializedName

data class TmapResponse (
    @SerializedName("searchPoiInfo") val info: TmapPois
)