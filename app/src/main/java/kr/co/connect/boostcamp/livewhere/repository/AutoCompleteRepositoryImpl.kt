package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.api.KakaoPlaceApi
import kr.co.connect.boostcamp.livewhere.model.KakaoPlaceResponse
import retrofit2.Response

class AutoCompleteRepositoryImpl(private val client: KakaoPlaceApi) : AutoCompleteRepository {
    override fun getAddress(query: String): Single<Response<List<KakaoPlaceResponse>>> {
        return client.getAddress(query)
    }
}