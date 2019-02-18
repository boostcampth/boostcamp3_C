package kr.co.connect.boostcamp.livewhere.model

import com.google.gson.annotations.SerializedName

data class TmapPoi(
    @SerializedName("poi") val pois:List<Poi>
)