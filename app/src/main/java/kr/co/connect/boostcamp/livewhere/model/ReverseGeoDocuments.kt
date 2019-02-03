package kr.co.connect.boostcamp.livewhere.model

import com.google.gson.annotations.SerializedName

data class ReverseGeoMeta(
    @SerializedName("total_count") val total_count: Int
)

data class ReverseGeoDocuments(
    @SerializedName("address") val addressMeta: ReverseGeoAddress
)

data class ReverseGeoAddress(
    @SerializedName("address_name") val addressName: String
)