package kr.co.connect.boostcamp.livewhere.model

import com.google.gson.annotations.SerializedName

data class HouseResponse(
    @SerializedName("address_result") val addr:Address,
    @SerializedName("house_result") val houseList:List<House>,
    @SerializedName("address_status_code") val addrStatusCode:Int,
    @SerializedName("house_rent_status_code") val houseStatusCode:Int
)