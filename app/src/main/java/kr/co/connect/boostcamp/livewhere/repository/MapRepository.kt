package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.model.HouseResponse

interface MapRepository{
    fun getPlace()
    fun getHouseDetail(address:String): Single<HouseResponse>
}