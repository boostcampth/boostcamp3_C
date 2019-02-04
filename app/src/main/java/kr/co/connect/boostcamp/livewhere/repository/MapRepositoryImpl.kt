package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.api.Api
import kr.co.connect.boostcamp.livewhere.api.ReverseGeoApi
import kr.co.connect.boostcamp.livewhere.model.HouseResponse
import kr.co.connect.boostcamp.livewhere.model.ReverseGeo
import retrofit2.Response

class MapRepositoryImpl(private val api: Api, private val reverseGeoApiModule: ReverseGeoApi) : MapRepository {
    override fun getAddress(latitude:String, longitude:String, inputCoord:String): Single<Response<ReverseGeo>> {
        return reverseGeoApiModule.getAddress( latitude, longitude, inputCoord)
    }

    override fun getHouseDetail(address: String): Single<Response<HouseResponse>> {
        return api.getDetail(address)
    }

    override fun getPlace() {

    }

}