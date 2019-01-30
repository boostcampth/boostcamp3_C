package kr.co.connect.boostcamp.livewhere.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("place_result") val placeList:List<Place>,
    @SerializedName("status_code") val statusCode:Int
)