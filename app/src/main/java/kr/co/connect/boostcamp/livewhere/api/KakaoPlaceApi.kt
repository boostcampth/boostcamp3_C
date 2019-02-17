package kr.co.connect.boostcamp.livewhere.api

import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.model.KakaoPlaceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoPlaceApi {
    @GET("search/address.json")
    fun getAddress(
        @Query("query") query: String,
        @Header("Authorization") Authorization: String = "KakaoAK 6ef6886ea829b80be5cbc0e09d5fda96"
    ): Single<Response<List<KakaoPlaceResponse>>>

}