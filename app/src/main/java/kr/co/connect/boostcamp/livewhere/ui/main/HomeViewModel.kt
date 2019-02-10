package kr.co.connect.boostcamp.livewhere.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.co.connect.boostcamp.livewhere.model.Bookmark
import kr.co.connect.boostcamp.livewhere.model.RecentSearch
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel
import kr.co.connect.boostcamp.livewhere.util.SingleLiveEvent

class HomeViewModel() : BaseViewModel() {
    private val _bookmark = MutableLiveData<ArrayList<Bookmark>>()
    val bookmark: LiveData<ArrayList<Bookmark>>
        get() = _bookmark

    private val _recentSearch = MutableLiveData<ArrayList<RecentSearch>>()
    val recentSearch: LiveData<ArrayList<RecentSearch>>
        get() = _recentSearch

    private var _searchBtnClicked = SingleLiveEvent<Any>()
    val searchBtnClicked: LiveData<Any>
        get() = _searchBtnClicked

    init {

        val tempbookmarkvalue = arrayListOf<Bookmark>(
            Bookmark("https://newsimg.sedaily.com/2016/07/07/1KYRUQMJ0M_1.jpg", "서초구", "서초빌딩", true, "100", "1000"),
            Bookmark(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/Doona_Bae_promoting_The_Tunnel.png/250px-Doona_Bae_promoting_The_Tunnel.png",
                "광진구",
                "광진빌딩",
                false,
                "0",
                "10000"
            ),
            Bookmark(
                "https://pds.joins.com//news/component/htmlphoto_mmdata/201708/11/1db25117-8a4e-4798-9cd0-906bbb5d01e6.gif",
                "동대문구",
                "동대문빌딩",
                false,
                "300",
                "20000"
            ),
            Bookmark(
                "https://i.ytimg.com/vi/DUKctL41RBo/maxresdefault.jpg",
                "관악구",
                "관악빌딩",
                false,
                "400",
                "30000"
            ),
            Bookmark(
                "https://post-phinf.pstatic.net/MjAxNzA3MTRfMjY4/MDAxNTAwMDEyMzM5NzY4.NBHHrYSqe6RuAUUxKm1IPwvuuoI_6nWV99lXiyUubWAg.jHOUOaZjAhQwcMpie8yfUaXV6rGMzPQ9WjWvLoTq5y4g.JPEG/15.jpg?type=w1200",
                "서대문구",
                "서대문빌딩",
                false,
                "500",
                "400000"
            )
        )
        _bookmark.postValue(tempbookmarkvalue)
    }

    fun onSearchClicked() {
        _searchBtnClicked.call()
    }
}