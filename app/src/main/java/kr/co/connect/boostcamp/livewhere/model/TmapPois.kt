package kr.co.connect.boostcamp.livewhere.model

import com.google.gson.annotations.SerializedName

data class TmapPois(
    @SerializedName("totalCount") val totalCount:String,
    @SerializedName("count") val count:String,
    @SerializedName("pois") val poi:TmapPoi
)