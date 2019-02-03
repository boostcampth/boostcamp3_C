package kr.co.connect.boostcamp.livewhere.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.co.connect.boostcamp.livewhere.model.HouseAvgPrice
import kr.co.connect.boostcamp.livewhere.model.PastTransaction
import kr.co.connect.boostcamp.livewhere.repository.DetailRepository
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.util.SingleLiveEvent

class DetailViewModel(private val detailRepository: DetailRepository) : BaseViewModel() {

    private val _coordinate = MutableLiveData<String>()
    val coordinate: LiveData<String>
        get() = _coordinate

    private val _listHouseAvgPrice = MutableLiveData<ArrayList<HouseAvgPrice>>()
    val listHouseAvgPrice: LiveData<ArrayList<HouseAvgPrice>>
        get() = _listHouseAvgPrice

    private val _pastTransactionPre = MutableLiveData<ArrayList<PastTransaction>>() //detail page 의 5개 미리보기 리스트
    val pastTransactionPre: LiveData<ArrayList<PastTransaction>>
        get() = _pastTransactionPre

    private val _pastTransactionMore = MutableLiveData<ArrayList<PastTransaction>>() //거래내역 더보기의 전체 리스트
    val pastTransactionMore: LiveData<ArrayList<PastTransaction>>
        get() = _pastTransactionMore

    private val _transactionMoreClicked = SingleLiveEvent<Any>() // 뒤로가기 버튼
    val transactionMoreClicked: LiveData<Any>
        get() = _transactionMoreClicked


    init {
        // 필요 변수 초기화
    }

    fun onClickedMore(){ //과거 거래 내역 더보기 클릭
        _transactionMoreClicked.call()
    }

}