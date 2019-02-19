package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Observable
import kr.co.connect.boostcamp.livewhere.model.TmapResponse
import retrofit2.Response

interface AutoCompleteRepository {
    fun getAddress(query: String): Observable<Response<TmapResponse>>
}