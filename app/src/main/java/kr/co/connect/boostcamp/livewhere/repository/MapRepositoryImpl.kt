package kr.co.connect.boostcamp.livewhere.repository

import android.content.Context
import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.api.Api
import kr.co.connect.boostcamp.livewhere.api.ReverseGeoApi
import kr.co.connect.boostcamp.livewhere.model.HouseResponse
import kr.co.connect.boostcamp.livewhere.model.PlaceResponse
import kr.co.connect.boostcamp.livewhere.model.ReverseGeo
import retrofit2.Response

class MapRepositoryImpl(private val api: Api, private val reverseGeoApiModule: ReverseGeoApi, private val context: Context) : MapRepository {
    override fun getAddress(latitude: String, longitude: String, inputCoord: String): Single<Response<ReverseGeo>> {
        return reverseGeoApiModule.getAddress(latitude, longitude, inputCoord)
    }

    override fun getHouseDetail(address: String): Single<Response<HouseResponse>> {
        return api.getDetail(address)
    }

    override fun getPlace(lat: Any, lng: Any, radius: Any, category: Any): Single<Response<PlaceResponse>> {
        return api.getPlace(lat.toString(), lng.toString(), radius.toString(), category.toString())
    }
}