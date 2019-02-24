package kr.co.connect.boostcamp.livewhere.api

import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.model.ReverseGeo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ReverseGeoApi {
    @Headers("Authorization: KakaoAK " + BuildConfig.KakaoServiceKey)
    @GET("geo/coord2address.json")
    fun getAddress(
        @Query("y") latitude: String,
        @Query("x") longitude: String,
        @Query("input_coord") inputCoord: String
    ): Single<Response<ReverseGeo>>
}