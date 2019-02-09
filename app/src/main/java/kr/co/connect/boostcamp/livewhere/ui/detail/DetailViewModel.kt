package kr.co.connect.boostcamp.livewhere.ui.detail

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.firebase.FirebaseDatabaseRepository
import kr.co.connect.boostcamp.livewhere.model.*
import kr.co.connect.boostcamp.livewhere.model.entity.ReviewEntity
import kr.co.connect.boostcamp.livewhere.repository.DetailRepository
import kr.co.connect.boostcamp.livewhere.repository.ReviewRepository
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.util.*

class DetailViewModel(private val detailRepository: DetailRepository,private val reviewRepository: ReviewRepository) : BaseViewModel() {

    private val _markerInfo = MutableLiveData<MarkerInfo>() // 전체 데이터 리스트
    val markerInfo: LiveData<MarkerInfo>
        get() = _markerInfo

    private val _recentPrice = MutableLiveData<RecentPrice>() //가장 최근 전,월세 거래가격
    val recentPrice: LiveData<RecentPrice>
        get() = _recentPrice

    private val _coordinate = MutableLiveData<String>() //좌표
    val coordinate: LiveData<String>
        get() = _coordinate

    private val _avgPriceType = MutableLiveData<Int>() //시세 추이 차트 타입 (전.월세)
    val avgPriceType: LiveData<Int>
        get() = _avgPriceType

    private val _listCharterHouseAvgPrice = MutableLiveData<ArrayList<HouseAvgPrice>>() //년도별 가격 평균 리스트 (전세)
    val listCharterHouseAvgPrice: LiveData<ArrayList<HouseAvgPrice>>
        get() = _listCharterHouseAvgPrice

    //TODO : 전세,월세 버튼 클릭시 포커싱할 데이터에 대한 조건 처리
    private val _listMonthlyHouseAvgPrice = MutableLiveData<ArrayList<HouseAvgPrice>>() //년도별 가격 평균 리스트 (월세)
    val listMonthlyHouseAvgPrice: LiveData<ArrayList<HouseAvgPrice>>
        get() = _listMonthlyHouseAvgPrice

    private val _pastTransactionSort = MutableLiveData<Int>() //detail page 의 5개 미리보기 리스트
    val pastTransactionSort: LiveData<Int>
        get() = _pastTransactionSort

    private val _pastTransactionPre = MutableLiveData<ArrayList<PastTransaction>>() //detail page 의 5개 미리보기 리스트
    val pastTransactionPre: LiveData<ArrayList<PastTransaction>>
        get() = _pastTransactionPre

    private val _pastTransactionMore = MutableLiveData<ArrayList<PastTransaction>>() //거래내역 더보기의 전체 리스트
    val pastTransactionMore: LiveData<ArrayList<PastTransaction>>
        get() = _pastTransactionMore

    private val _transactionMoreClicked = SingleLiveEvent<Any>() // 뒤로가기 버튼
    val transactionMoreClicked: LiveData<Any>
        get() = _transactionMoreClicked

    private val _reviewMoreClicked = SingleLiveEvent<Any>()
    val reviewMoreClicked: LiveData<Any>
        get() = _reviewMoreClicked

    private val _reviewPostOpenClicked = SingleLiveEvent<Any>()
    val reviewPostOpenClicked: LiveData<Any>
        get() = _reviewPostOpenClicked

    private val _reviewPostClicked = SingleLiveEvent<Any>()
    val reviewPostClicked: LiveData<Any>
        get() = _reviewPostClicked

    private val _commentsList = MutableLiveData<List<Review>>()
    fun getComments():LiveData<List<Review>> {
        if (_commentsList.value == null) {
            loadComments("1234567890123456789") // TODO: markerInfo 에서 현재 페이지 pnu코드 인자로 넘기기.
        }
        return _commentsList
    }

    val postReviewNickname = ObservableField<String>()
    val postReviewContents = ObservableField<String>()

    init {

    }

    override fun onCleared() {
        super.onCleared()
        reviewRepository.removeListener()
    }

    fun onClickedTransactionMore() { //과거 거래 내역 더보기 클릭
        _transactionMoreClicked.call()
    }

    fun onClickedReviewMore() { //리뷰 더보기 클릭
        _reviewMoreClicked.call()
    }

    fun onClickedReviewPostOpen() {
        _reviewPostOpenClicked.call()
    }

    fun onClickedReviewPost(){
        _reviewPostClicked.call()
    }

    fun onClickedAvgPriceType(view: View) {
        when (view.id) {
            R.id.detail_fragment_btn_trend_price_charter -> _avgPriceType.postValue(TYPE_CHARTER)
            R.id.detail_fragment_btn_trend_price_monthly -> _avgPriceType.postValue(TYPE_MONTHLY)
        }
    }

    fun getCoordinateFromInfo() {
        _coordinate.postValue(_markerInfo.value!!.latLng.latitude.toString() + "," + _markerInfo.value!!.latLng.longitude.toString())
    }

    fun getAvgPriceList(): LiveData<ArrayList<HouseAvgPrice>> {
        return when (_avgPriceType.value) {
            TYPE_CHARTER -> listCharterHouseAvgPrice
            TYPE_MONTHLY -> listMonthlyHouseAvgPrice
            else -> listCharterHouseAvgPrice
        }
    }

    fun getRecentPriceFromInfo() { //전체 데이터로 부터 최근거래가, 년도별 평균 거래가 추출
        if (_markerInfo.value!!.statusCode == StatusCode.RESULT_204) {
            val result = RecentPrice("전세 정보 없음", "월세 정보 없음")
            _recentPrice.postValue(result)
        } else if (_markerInfo.value!!.statusCode == StatusCode.RESULT_200) {
            val charterList = ArrayList<House>().apply {
                addAll(_markerInfo.value!!.houseList.filter { it.rentCase == "전세" }) // 전체데이터의 전세 데이터만 추가
                sortedWith(CompareByContractYM) // 계약년월(최근순)순으로 정렬 }
            }
            val monthlyList = ArrayList<House>().apply {
                addAll(_markerInfo.value!!.houseList.filter { it.rentCase == "월세" })// 전체데이터의 월세 데이터만 추가
                sortedWith(CompareByContractYM) // 계약년월(최근순)순으로 정렬
            }
            val recentPrice:RecentPrice
            when {
                charterList.isEmpty() -> recentPrice = RecentPrice(
                    "정보 없음",
                    monthlyList[0].fee
                )
                monthlyList.isEmpty() -> recentPrice = RecentPrice(
                    charterList[0].deposite,
                    "정보 없음"
                )
                else -> recentPrice = RecentPrice(
                    charterList[0].deposite,
                    monthlyList[0].fee
                )
            }//가장 최근 전,월세 데이터 반영 //TODO : 전세 or 월세 정보 없을 경우 예외처리
            _recentPrice.postValue(recentPrice)

            getAvgPriceFromList(charterList, monthlyList)
        }
    }

    fun getAvgPriceFromList(charterList: ArrayList<House>, monthlyList: ArrayList<House>) {
        val cList = ArrayList<HouseAvgPrice>()
        val mList = ArrayList<HouseAvgPrice>()
        val cMap = HashMap<Float, ArrayList<Float>>()
        val mMap = HashMap<Float, ArrayList<Float>>()

        charterList.forEach {
            if (cMap[it.contractYear.toFloat()] == null) { //해당 년도 데이터 첫 추가시
                cMap[it.contractYear.toFloat()] = ArrayList()
            }
            cMap[it.contractYear.toFloat()]!!.add(it.deposite.toFloat())

        }
        for (it in cMap) {
            var sum = 0f
            it.value.forEach { sum += it }
            it.value[0] = sum / it.value.size.toFloat()
            cList.add(HouseAvgPrice(it.key, it.value[0]))
        }
        _listCharterHouseAvgPrice.postValue(cList)

        monthlyList.forEach {
            if (mMap[it.contractYear.toFloat()] == null) {
                mMap[it.contractYear.toFloat()] = ArrayList()
            }
            mMap[it.contractYear.toFloat()]!!.add(it.fee.toFloat())
        }
        for (it in mMap) {
            var sum = 0f
            it.value.forEach { sum += it }
            it.value[0] = sum / it.value.size.toFloat()
            mList.add(HouseAvgPrice(it.key, it.value[0]))
        }
        _listMonthlyHouseAvgPrice.postValue(mList)
        //TODO : 코드 양 정리..
    }

    fun getPastTransactionFromList() {
        if (_markerInfo.value!!.statusCode == StatusCode.RESULT_204) {
            //TODO : 거래 정보 없을때 처리
        } else if (_markerInfo.value!!.statusCode == StatusCode.RESULT_200) {
            val transactionList = ArrayList<PastTransaction>()
            _markerInfo.value!!.houseList.forEach {
                if (it.rentCase == "전세") {
                    val item = PastTransaction(it.name, it.deposite, it.area, it.rentCase, it.contractYear)
                    transactionList.add(item)
                } else if (it.rentCase == "월세") {
                    val item =
                        PastTransaction(it.name, it.deposite + "/" + it.fee, it.area, it.rentCase, it.contractYear)
                    transactionList.add(item)
                }
            }
            _pastTransactionMore.postValue(transactionList)
            val tempList = ArrayList<PastTransaction>()
            for (i in 0..2) {
                if (i >= transactionList.size)
                    break
                tempList.add(transactionList[i])
            }
            _pastTransactionPre.postValue(tempList)
        }
    }

    fun setPastTransactionSort(view: View) {
        when (view.id) {
            R.id.past_transaction_more_sort_by_area -> _pastTransactionSort.postValue(SORT_BY_AREA)
            R.id.past_transaction_more_sort_by_type -> _pastTransactionSort.postValue(SORT_BY_TYPE)
            R.id.past_transaction_more_sort_by_contract_year -> _pastTransactionSort.postValue(SORT_BY_YEAR)
        }
    }

    fun sortTransactionList() {
        if (!(_pastTransactionMore.value.isNullOrEmpty())) {
            when (_pastTransactionSort.value) {
                SORT_BY_AREA -> {
                    _pastTransactionMore.value!!.sortWith(CompareByArea)
                }
                SORT_BY_TYPE -> {
                    _pastTransactionMore.value!!.sortWith(CompareByType)
                }
                SORT_BY_YEAR -> {
                    _pastTransactionMore.value!!.sortWith(CompareByYear)
                } // TODO 역순으로 정렬 기능 구현.
            }
        }
    }

    fun loadComments(pnu:String){
        reviewRepository.addListener(pnu,object : FirebaseDatabaseRepository.FirebaseDatabaseRepositoryCallback<Review> {

            override fun onSuccess(result: List<Review>) {
                _commentsList.postValue(result)
            }

            override fun onError(e: Exception) {
                //TODO : 후기 가져오기 실패 예외 처리
            }
        })
    }

    fun postComment(review:ReviewEntity){
        reviewRepository.postReview(review).addOnCompleteListener {
            // TODO : 게시 성공 처리
        }.addOnFailureListener {
            // TODO : 게시 실패 처리
        }
    }

}