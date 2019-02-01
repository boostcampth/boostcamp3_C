package kr.co.connect.boostcamp.livewhere.api

import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.model.HouseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api{
    @GET("house/search/infos")
    fun getHouseDetail(
        @Query("address")address:String
    ): Single<HouseResponse>
}