package kr.co.connect.boostcamp.livewhere.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.co.connect.boostcamp.livewhere.model.HouseAvgPrice
import kr.co.connect.boostcamp.livewhere.model.PastTransaction
import kr.co.connect.boostcamp.livewhere.repository.DetailRepository
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel

class DetailViewModel(private val detailRepository: DetailRepository) : BaseViewModel() {

    private val _coordinate = MutableLiveData<String>()
    val coordinate: LiveData<String>
        get() = _coordinate

    private val _listHouseAvgPrice = MutableLiveData<ArrayList<HouseAvgPrice>>()
    val listHouseAvgPrice: LiveData<ArrayList<HouseAvgPrice>>
        get() = _listHouseAvgPrice

    private val _pastTransaction = MutableLiveData<ArrayList<PastTransaction>>()
    val pastTransaction: LiveData<ArrayList<PastTransaction>>
        get() = _pastTransaction

    init {
        // 필요 변수 초기화
    }

}