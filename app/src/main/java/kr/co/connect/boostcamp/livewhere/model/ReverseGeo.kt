package kr.co.connect.boostcamp.livewhere.model

import com.google.gson.annotations.SerializedName


data class ReverseGeo(
    @SerializedName("meta") val metaData: ReverseGeoMeta,
    @SerializedName("documents") val documentData: List<ReverseGeoDocuments>
)

