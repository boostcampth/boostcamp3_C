package kr.co.connect.boostcamp.livewhere.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.co.connect.boostcamp.livewhere.model.HouseAvgPrice
import kr.co.connect.boostcamp.livewhere.repository.DetailRepository
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel

class DetailViewModel(private val detailRepository: DetailRepository) : BaseViewModel(){

    private val _coordinate = MutableLiveData<String>()
    val coordinate : LiveData<String>
        get() = _coordinate

    private val _listHouseAvgPrice = MutableLiveData<ArrayList<HouseAvgPrice>>()
    val listHouseAvgPrice : LiveData<ArrayList<HouseAvgPrice>>
        get() = _listHouseAvgPrice

    init {
        _coordinate.postValue("37.515238,127.032937")
    }

}