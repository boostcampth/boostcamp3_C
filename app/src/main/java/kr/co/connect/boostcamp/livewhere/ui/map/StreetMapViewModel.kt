package kr.co.connect.boostcamp.livewhere.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.connect.boostcamp.livewhere.model.ReverseGeo
import kr.co.connect.boostcamp.livewhere.repository.MapRepositoryImpl
import retrofit2.Response


interface OnStreetMapViewModelInterface {
    fun onAddressData(lat: Double, lng: Double)
}

class StreetMapViewModel(val mapRepository: MapRepositoryImpl) : ViewModel(), OnStreetMapViewModelInterface {
    private val _addressLiveData: MutableLiveData<String> = MutableLiveData()
    val addressLiveData: LiveData<String>
        get() = _addressLiveData

    override fun onAddressData(lat: Double, lng: Double) {
        mapRepository.getAddress(lat.toString(), lng.toString(), "WGS84")
            .filter { response: Response<ReverseGeo> -> response.isSuccessful }.subscribe({ response ->
                _addressLiveData.postValue(response.body()?.documentData!![0].addressMeta.addressName)
            }, {

            })
    }
}