package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.model.HouseResponse
import kr.co.connect.boostcamp.livewhere.model.PlaceResponse
import kr.co.connect.boostcamp.livewhere.model.ReverseGeo
import retrofit2.Response

interface MapRepository {
    fun getPlace(lat: Any, lng: Any, radius: Any, category: Any): Single<Response<PlaceResponse>>
    fun getHouseDetail(address: String): Single<Response<HouseResponse>>
    fun getAddress(latitude: String, longitude: String, inputCoord: String): Single<Response<ReverseGeo>>
    fun getHouseDetailWithNotCompletedAddress(address: String): Single<Response<HouseResponse>>
}