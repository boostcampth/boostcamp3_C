package kr.co.connect.boostcamp.livewhere.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api{

    @GET("house/search/info")
    fun getHouseDetail(
        @Query("address")address:String
    ): Single<Response<List<Any>>>
}