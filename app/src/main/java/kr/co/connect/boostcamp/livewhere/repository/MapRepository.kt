package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.model.HouseResponse
import kr.co.connect.boostcamp.livewhere.model.ReverseGeo
import retrofit2.Response

interface MapRepository{
    fun getPlace()
    fun getHouseDetail(address:String): Single<Response<HouseResponse>>
    fun getAddress(latitude:String, longitude:String, inputCoord:String) : Single<Response<ReverseGeo>>
}