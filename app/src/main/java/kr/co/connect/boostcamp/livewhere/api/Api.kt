package kr.co.connect.boostcamp.livewhere.api

import android.provider.ContactsContract
import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.model.HouseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @GET("house/search/info")
    fun getHouseDetail(
        @Query("address") address: String
    ): Single<Response<List<Any>>>

    @GET("house/search/infos")
    fun getDetail(
        @Query("address") address: String
    ): Single<HouseResponse>

    @POST("")
    fun postReview(
        @Query("nickname") nickname: String, @Query("id") id: String, @Query("contents") contents: String
    ) : Single<Response<Any>>
}