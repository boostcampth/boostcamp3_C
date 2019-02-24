package kr.co.connect.boostcamp.livewhere.api

import io.reactivex.Observable
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.model.TmapResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmapApi {
    @GET("tmap/pois")
    fun getAddress(
        @Query("searchKeyword") searchKeyword: String,
        @Query("appKey") appKey: String = BuildConfig.TmapApiKey,
        @Query("count") count: String = "10",
        @Query("areaLLCode") code: String = "11"
    ): Observable<Response<TmapResponse>>


}