package kr.co.connect.boostcamp.livewhere.ui.detail

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.data.SharedPreferenceStorage
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
import kr.co.connect.boostcamp.livewhere.firebase.BookmarkUserDatabaseRepository
import kr.co.connect.boostcamp.livewhere.firebase.ReviewDatabaseRepository
import kr.co.connect.boostcamp.livewhere.model.*
import kr.co.connect.boostcamp.livewhere.model.entity.BookmarkUserEntity
import kr.co.connect.boostcamp.livewhere.model.entity.ReviewEntity
import kr.co.connect.boostcamp.livewhere.repository.*
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.util.*

class DetailViewModel(
    private val detailRepository: DetailRepository
    , private val reviewRepository: ReviewRepository
    , private val bookmarkUserRepository: BookmarkUserRepository
    , private val bookmarkRepository: BookmarkRepositoryImpl
    , private val pref: SharedPreferenceStorage
) :
    BaseViewModel() {

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

    private val _listCharterHouseAvgPrice = MutableLiveData<List<HouseAvgPrice>>() //년도별 가격 평균 리스트 (전세)
    val listCharterHouseAvgPrice: LiveData<List<HouseAvgPrice>>
        get() = _listCharterHouseAvgPrice

    //TODO : 전세,월세 버튼 클릭시 포커싱할 데이터에 대한 조건 처리
    private val _listMonthlyHouseAvgPrice = MutableLiveData<List<HouseAvgPrice>>() //년도별 가격 평균 리스트 (월세)
    val listMonthlyHouseAvgPrice: LiveData<List<HouseAvgPrice>>
        get() = _listMonthlyHouseAvgPrice

    private val _pastTransactionSort = MutableLiveData<Int>()  // 정렬 종류 상태
    val pastTransactionSort: LiveData<Int>
        get() = _pastTransactionSort

    private val _pastTransactionPre = MutableLiveData<ArrayList<PastTransaction>>() //detail page 의 5개 미리보기 리스트
    val pastTransactionPre: LiveData<ArrayList<PastTransaction>>
        get() = _pastTransactionPre

    private val _pastTransactionMore = MutableLiveData<ArrayList<PastTransaction>>() //거래내역 더보기의 전체 리스트
    val pastTransactionMore: LiveData<ArrayList<PastTransaction>>
        get() = _pastTransactionMore

    private val _isBookmarked = MutableLiveData<Boolean>()
    val isBookmarked: LiveData<Boolean>
        get() = _isBookmarked

    private val _hasLoaded = MutableLiveData<Boolean>()
    val hasLoaded: LiveData<Boolean>
        get() = _hasLoaded

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

    private val _reviewPostSuccess = SingleLiveEvent<Any>()
    val reviewPostSuccess: LiveData<Any>
        get() = _reviewPostSuccess

    private val _onPressedBackBtn = SingleLiveEvent<Any>()
    val onPressedBackBtn: LiveData<Any>
        get() = _onPressedBackBtn

    private val _onPressedBookmarkBtn = SingleLiveEvent<Any>()
    val onPressedBookmarkBtn: LiveData<Any>
        get() = _onPressedBookmarkBtn

    private val _commentsList = MutableLiveData<List<Review>>()
    fun getComments(): LiveData<List<Review>> {
        if (_commentsList.value == null) {
            loadComments(pnuCode.get()!!) // TODO: markerInfo 에서 현재 페이지 pnu코드 인자로 넘기기.
        }
        return _commentsList
    }

    private val _bookmarksList = MutableLiveData<List<BookmarkUser>>()
    fun getBookmarks(): LiveData<List<BookmarkUser>> {
        if (_commentsList.value == null) {
            loadBookmarks(pnuCode.get()!!) // TODO: markerInfo 에서 현재 페이지 pnu코드 인자로 넘기기.
        }
        return _bookmarksList
    }

    val address = ObservableField<String>()
    val buildingName = ObservableField<String>()
    val pnuCode = ObservableField<String>()
    val postReviewNickname = ObservableField<String>()
    val postReviewContents = ObservableField<String>()


    init {
        _isBookmarked.value = false
        _hasLoaded.value = false
    }

    override fun onCleared() {
        super.onCleared()
        reviewRepository.removeListener()
    }

    fun onPressedBackButton() {
        _onPressedBackBtn.call()
    }

    fun onPressedBookmarkButton() {
        _onPressedBookmarkBtn.call()
        when (_isBookmarked.value) {
            false -> {
                val bookmarkLocal = BookmarkEntity(address.get()!!, buildingName.get()!!, _coordinate.value!!)
                insertBookmarkToLocal(bookmarkLocal)
            }
            true -> {
                deleteBookmarkFromLocal(address.get()!!)
            }
        }
    }

    fun onClickedTransactionMore() { //과거 거래 내역 더보기 클릭
        _transactionMoreClicked.call()
    }

    fun onClickedReviewMore() { //리뷰 더보기 클릭
        _reviewMoreClicked.call()
    }

    fun onClickedReviewPostOpen(view: View) {
        if (_hasLoaded.value == true) {
            var isPassed = true
            _commentsList.value!!.forEach {
                if (it.id.equals(pref.uuid)) {
                    Toast.makeText(view.context, POST_CLICK_ERROR, Toast.LENGTH_SHORT).show()
                    isPassed = false
                    return
                }
            }
            if (isPassed) {
                _reviewPostOpenClicked.call()
            }
        }
    }

    fun onClickedReviewPost(view: View) {
        when {
            postReviewContents.get().isNullOrEmpty() -> Toast.makeText(
                view.context,
                view.context.getString(R.string.empty_contents),
                Toast.LENGTH_SHORT
            ).show()
            postReviewNickname.get().isNullOrEmpty() -> Toast.makeText(
                view.context,
                view.context.getString(R.string.empty_nickname),
                Toast.LENGTH_SHORT
            ).show()
            else -> {
                _reviewPostClicked.call()
                postReviewNickname.set(null)
                postReviewContents.set(null)
            }
        }
    }

    fun onClickedAvgPriceType(view: View) {
        when (view.id) {
            R.id.detail_fragment_btn_trend_price_charter -> _avgPriceType.postValue(TYPE_CHARTER)
            R.id.detail_fragment_btn_trend_price_monthly -> _avgPriceType.postValue(TYPE_MONTHLY)
        }
    }

    fun setMarkerInfoFromActivity(markerInfo: MarkerInfo) { //markerInfo 데이터 수신
        if (markerInfo.address.name.isEmpty()) {
            address.set(markerInfo.address.addr)
            buildingName.set("건물명 없음")
        } else {
            address.set(markerInfo.address.addr)
            buildingName.set(markerInfo.address.name)
        }
        pnuCode.set(markerInfo.address.pnuCode)
        _markerInfo.postValue(markerInfo)
    }

    fun getCoordinateFromInfo() {
        _coordinate.postValue(_markerInfo.value!!.latLng.latitude.toString() + "," + _markerInfo.value!!.latLng.longitude.toString())
    }

    fun getAvgPriceList(): LiveData<List<HouseAvgPrice>> {
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
                addAll(_markerInfo.value!!.houseList.filter { it.rentCase == "월세" || it.rentCase == "준월세" })// 전체데이터의 월세 데이터만 추가
                sortedWith(CompareByContractYM) // 계약년월(최근순)순으로 정렬
            }
            val recentPrice: RecentPrice
            recentPrice = when {
                charterList.isEmpty() -> RecentPrice(
                    "정보 없음",
                    monthlyList[0].fee
                )
                monthlyList.isEmpty() -> RecentPrice(
                    charterList[0].deposite,
                    "정보 없음"
                )
                else -> RecentPrice(
                    charterList[0].deposite,
                    monthlyList[0].fee
                )
            }
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
            cList.sortWith(SortByYear)
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
            mList.sortWith(SortByYear)
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
                    val item = PastTransaction(
                        buildingName.get() + " " + it.dong,
                        it.deposite,
                        it.area,
                        it.rentCase,
                        it.contractYear
                    )
                    transactionList.add(item)
                } else if (it.rentCase == "월세" || it.rentCase == "준월세") {
                    val item =
                        PastTransaction(
                            buildingName.get() + " " + it.dong,
                            it.deposite + "/" + it.fee,
                            it.area,
                            "월세",
                            it.contractYear
                        )
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
            R.id.past_transaction_header_area -> {
                if (_pastTransactionSort.value == SORT_BY_AREA)
                    _pastTransactionSort.postValue(SORT_BY_AREA_REV)
                else
                    _pastTransactionSort.postValue(SORT_BY_AREA)
            }
            R.id.past_transaction_header_type -> {
                if (_pastTransactionSort.value == SORT_BY_TYPE)
                    _pastTransactionSort.postValue(SORT_BY_TYPE_REV)
                else
                    _pastTransactionSort.postValue(SORT_BY_TYPE)
            }
            R.id.past_transaction_header_contract_year -> {
                if (_pastTransactionSort.value == SORT_BY_YEAR)
                    _pastTransactionSort.postValue(SORT_BY_YEAR_REV)
                else
                    _pastTransactionSort.postValue(SORT_BY_YEAR)
            }
        }
    }

    fun sortTransactionList() {
        if (!(_pastTransactionMore.value.isNullOrEmpty())) {
            when (_pastTransactionSort.value) {
                SORT_BY_AREA -> {
                    _pastTransactionMore.value!!.sortWith(CompareByArea)
                }
                SORT_BY_AREA_REV -> {
                    _pastTransactionMore.value!!.sortWith(CompareByAreaRev)
                }
                SORT_BY_TYPE -> {
                    _pastTransactionMore.value!!.sortWith(CompareByType)
                }
                SORT_BY_TYPE_REV -> {
                    _pastTransactionMore.value!!.sortWith(CompareByTypeRev)
                }
                SORT_BY_YEAR -> {
                    _pastTransactionMore.value!!.sortWith(CompareByYear)
                }
                SORT_BY_YEAR_REV -> {
                    _pastTransactionMore.value!!.sortWith(CompareByYearRev)
                }
            }
        }
    }

    fun loadComments(pnu: String) {
        reviewRepository.addListener(pnu,
            object : ReviewDatabaseRepository.FirebaseDatabaseRepositoryCallback<Review> {

                override fun onSuccess(result: List<Review>) {
                    _commentsList.postValue(result)
                }

                override fun onError(e: Exception) {
                    //TODO : 후기 가져오기 실패 예외 처리
                }
            })
    }

    fun postComment() {
        val review = ReviewEntity(
            postReviewNickname.get(),
            pref.uuid,
            DateUtil.getCurrentDate(),
            postReviewContents.get(),
            pnuCode.get()
        )
        // TODO : MarkerInfo 구조를 변경하여 PNU코드를 _markerinfo에 담을수 있도록 수정
        reviewRepository.postReview(review).addOnSuccessListener {
            _reviewPostSuccess.call()
        }.addOnFailureListener {
            // TODO : 게시 실패 처리
        }
    }

    fun loadBookmarks(pnu: String) {
        bookmarkUserRepository.addListener(pnu,
            object : BookmarkUserDatabaseRepository.FirebaseDatabaseRepositoryCallback<BookmarkUser> {
                override fun onSuccess(result: List<BookmarkUser>) {
                    _bookmarksList.postValue(result)
                    _hasLoaded.postValue(true)
                }

                override fun onError(e: Exception) {
                    _hasLoaded.postValue(true)
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
    }

    fun postBookmark() {
        val bookmarkUser = BookmarkUserEntity(pref.uuid)
//        insertBookmarkToLocal(bookmarkLocal)
        bookmarkUserRepository.addBookmark(pnuCode.get()!!, bookmarkUser)
            .addOnSuccessListener {
                loadBookmarks(pnuCode.get()!!)
                _hasLoaded.postValue(true)
            }.addOnFailureListener {
                Log.e("Error:", "북마크 추가 실패 ")
                //TODO : 북마크 추가 실패시 처리
                _hasLoaded.postValue(true)
            }
    }

    fun deleteBookmark() {
        bookmarkUserRepository.deleteBookmark(pnuCode.get()!!, pref.uuid!!)
            .addOnSuccessListener {
                loadBookmarks(pnuCode.get()!!)
                _hasLoaded.postValue(true)
            }.addOnFailureListener {
                //TODO : 북마크 추가 실패시 처리
                _hasLoaded.postValue(true)
            }
    }

    fun checkBookmarkId() {
        if (_bookmarksList.value.isNullOrEmpty()) {
            _isBookmarked.postValue(false)
        } else {
            _bookmarksList.value!!.forEach {
                if (it.uuid.equals(pref.uuid)) {
                    _isBookmarked.postValue(true)
                    return
                }
                _isBookmarked.postValue(false)
            }
        }
    }

    fun setUuid(uuid: String?) {
        if (pref.uuid.isNullOrEmpty()) {
            pref.uuid = uuid
        }
    }

    fun insertBookmarkToLocal(bookmarkEntity: BookmarkEntity) {
        _hasLoaded.postValue(false)
        addDisposable(bookmarkRepository.setBookmark(bookmarkEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it > 0) {
                    postBookmark()
                }
            })
    }

    fun deleteBookmarkFromLocal(address: String) {
        _hasLoaded.postValue(false)
        addDisposable(bookmarkRepository.deleteBookmark(address)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it > 0) {
                    deleteBookmark()
                }
            })
    }

}