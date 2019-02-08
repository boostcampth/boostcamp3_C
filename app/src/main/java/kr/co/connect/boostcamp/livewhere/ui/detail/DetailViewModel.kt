package kr.co.connect.boostcamp.livewhere.ui.detail

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.naver.maps.geometry.LatLng
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.model.*
import kr.co.connect.boostcamp.livewhere.repository.DetailRepository
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.util.*

class DetailViewModel(private val detailRepository: DetailRepository) : BaseViewModel() {

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

    private val _reviewList = MutableLiveData<ArrayList<Review>>() //거주 후기 정보 전체 리스트
    val reviewList: LiveData<ArrayList<Review>>
        get() = _reviewList

    private val _transactionMoreClicked = SingleLiveEvent<Any>() // 뒤로가기 버튼
    val transactionMoreClicked: LiveData<Any>
        get() = _transactionMoreClicked

    private val _reviewMoreClicked = SingleLiveEvent<Any>()
    val reviewMoreClicked: LiveData<Any>
        get() = _reviewMoreClicked

    private val _reviewPostClicked = SingleLiveEvent<Any>()
    val reviewPostClicked: LiveData<Any>
        get() = _reviewPostClicked

    init {
        _pastTransactionSort.postValue(SORT_BY_AREA)
        _avgPriceType.postValue(TYPE_CHARTER)
        val latLng = LatLng(37.5390102, 127.0685085)
        val charterList = ArrayList<House>()
        val monthlyList = ArrayList<House>()
        val house = House("asd", "asd", "asd", "asd", "asd", "asd", "전세", "2000", "30", "2013", "201305", "1")
        val house2 = House("asd", "asd", "asd", "asd", "asd", "asd", "월세", "3000", "30", "2012", "201202", "1")
        val house3 = House("asd", "asd", "asd", "asd", "asd", "asd", "전세", "2000", "30", "2014", "201401", "1")
        val house4 = House("asd", "asd", "asd", "asd", "asd", "asd", "전세", "5000", "30", "2010", "201002", "1")
        val house5 = House("asd", "asd", "asd", "asd", "asd", "asd", "전세", "4500", "30", "2014", "201401", "1")
        val house6 = House("asd", "asd", "asd", "asd", "asd", "asd", "전세", "3500", "30", "2011", "201112", "1")
        val house7 = House("asd", "asd", "asd", "asd", "asd", "asd", "월세", "3500", "50", "2010", "201012", "1")
        val house8 = House("asd", "asd", "asd", "asd", "asd", "asd", "월세", "3500", "80", "2013", "201312", "1")
        charterList.add(house)
        charterList.add(house2)
        charterList.add(house3)
        charterList.add(house4)
        charterList.add(house5)
        charterList.add(house6)
        charterList.add(house7)
        charterList.add(house8)
        val markerInfo = MarkerInfo(latLng, charterList, StatusCode.RESULT_200)
        _markerInfo.postValue(markerInfo)

    }

    fun onClickedTransactionMore() { //과거 거래 내역 더보기 클릭
        _transactionMoreClicked.call()
    }

    fun onClickedReviewMore() { //리뷰 더보기 클릭
        _reviewMoreClicked.call()
    }

    fun onClickedReviewPost() {
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
        Log.d("@@@", "in")
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
                }
            }
        }
    }

    //TODO : 과거 거래 내역 더보기 정렬기준 별 기능 구현.
}