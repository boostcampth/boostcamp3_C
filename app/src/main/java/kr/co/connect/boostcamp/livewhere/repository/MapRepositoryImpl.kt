package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.api.Api
import kr.co.connect.boostcamp.livewhere.model.HouseResponse

class MapRepositoryImpl(private val api : Api) : MapRepository{
    override fun getHouseDetail(address: String): Single<HouseResponse> {
        return api.getDetail(address)
    }

    override fun getPlace() {

    }

}