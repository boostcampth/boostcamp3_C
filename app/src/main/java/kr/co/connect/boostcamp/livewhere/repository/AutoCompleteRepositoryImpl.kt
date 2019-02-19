package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Observable
import kr.co.connect.boostcamp.livewhere.api.TmapApi
import kr.co.connect.boostcamp.livewhere.model.TmapResponse
import retrofit2.Response

class AutoCompleteRepositoryImpl(private val client: TmapApi) : AutoCompleteRepository {
    override fun getAddress(query: String): Observable<Response<TmapResponse>> {
        return client.getAddress(query)
    }
}