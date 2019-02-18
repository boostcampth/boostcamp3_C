package kr.co.connect.boostcamp.livewhere.repository

import android.util.Log
import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.api.TmapApi
import kr.co.connect.boostcamp.livewhere.model.TmapResponse
import retrofit2.Response

class AutoCompleteRepositoryImpl(private val client: TmapApi) : AutoCompleteRepository {
    override fun getAddress(query: String): Single<Response<TmapResponse>> {
        Log.d("ACRI", "ACRI STARTED")
        return client.getAddress(query)
    }
}