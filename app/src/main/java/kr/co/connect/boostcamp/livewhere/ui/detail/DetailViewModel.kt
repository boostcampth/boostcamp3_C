package kr.co.connect.boostcamp.livewhere.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.Transaction
import kr.co.connect.boostcamp.livewhere.model.HouseAvgPrice
import kr.co.connect.boostcamp.livewhere.model.PastTransaction
import kr.co.connect.boostcamp.livewhere.model.Review
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

    private val _reviewList =  MutableLiveData<ArrayList<Review>>() //거래내역 더보기의 전체 리스트
    val reviewList: LiveData<ArrayList<Review>>
        get() = _reviewList

    private val _transactionMoreClicked = SingleLiveEvent<Any>() // 뒤로가기 버튼
    val transactionMoreClicked: LiveData<Any>
        get() = _transactionMoreClicked

    private val _reviewMoreClicked = SingleLiveEvent<Any>()
    val reviewMoreClicked : LiveData<Any>
        get() = _reviewMoreClicked

    private val _reviewPostClicked = SingleLiveEvent<Any>()
    val reviewPostClicked : LiveData<Any>
        get() = _reviewPostClicked

    init {
        // 필요 변수 초기화
//        val list = ArrayList<PastTransaction>()
//        val list2 = ArrayList<Review>()
//
//        list.add(PastTransaction("가나 빌딩","10000","55","전세","2011"))
//        list.add(PastTransaction("다라 빌딩","200/50","57","월세","2013"))
//        list.add(PastTransaction("마바 빌딩","20000","75","전세","2014"))
//
//        list2.add(Review("주녕","010-45**-****","주차 공간이 넓고 편의시설이 많아 살기 좋은곳 교통편도 잘 되어있음"))
//
//        _pastTransactionMore.postValue(list)
//        _pastTransactionPre.postValue(list)
//        _reviewList.postValue(list2)

    }

    fun onClickedTransactionMore(){ //과거 거래 내역 더보기 클릭
        _transactionMoreClicked.call()
    }

    fun onClickedReviewMore(){ //리뷰 더보기 클릭
        _reviewMoreClicked.call()
    }

    fun onClickedReviewPost(){
        _reviewPostClicked.call()
    }
}