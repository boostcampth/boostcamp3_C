package kr.co.connect.boostcamp.livewhere.api

import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.model.KakaoPlaceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoPlaceApi {
    @GET("search/address.json")
    fun getAddress(
        @Query("query") query: String,
        @Header("Authorization") Authorization: String
    ): Single<Response<List<KakaoPlaceResponse>>>

}