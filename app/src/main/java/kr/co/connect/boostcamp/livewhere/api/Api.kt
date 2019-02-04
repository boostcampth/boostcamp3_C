package kr.co.connect.boostcamp.livewhere.api

import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.model.HouseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api{
    @GET("house/search/info")
    fun getHouseDetail(
        @Query("address")address:String
    ):Single<Response<List<Any>>>
    // FIXME Response로 둘 필요 없습니다. Single<<List<Any>> 로 변경하고 사용하는게 더 좋습니다. 모든 API의 return값들을 바꿔주세요
    
    @GET("house/search/infos")
    fun getDetail(
        @Query("address")address:String
    ): Single<Response<HouseResponse>>
}