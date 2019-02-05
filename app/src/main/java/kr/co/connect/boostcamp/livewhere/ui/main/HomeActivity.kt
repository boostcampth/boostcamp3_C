package kr.co.connect.boostcamp.livewhere.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.view.*
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityHomeBinding
import kr.co.connect.boostcamp.livewhere.model.Bookmark
import kr.co.connect.boostcamp.livewhere.ui.main.adapter.BookmarkRecyclerViewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeActivityViewModel by viewModel()
    private val tempbookmarkvalue = arrayListOf<Bookmark>(
        Bookmark("https://newsimg.sedaily.com/2016/07/07/1KYRUQMJ0M_1.jpg", "서초구", "서초빌딩", true, 100, 1000),
        Bookmark(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/Doona_Bae_promoting_The_Tunnel.png/250px-Doona_Bae_promoting_The_Tunnel.png",
            "광진구",
            "광진빌딩",
            false,
            0,
            10000),
        Bookmark("https://pds.joins.com//news/component/htmlphoto_mmdata/201708/11/1db25117-8a4e-4798-9cd0-906bbb5d01e6.gif",
        "동대문구",
            "동대문빌딩",
            false,
            300,
            20000),
        Bookmark("https://i.ytimg.com/vi/DUKctL41RBo/maxresdefault.jpg",
            "관악구",
            "관악빌딩",
            false,
            400,
            30000),
        Bookmark("https://post-phinf.pstatic.net/MjAxNzA3MTRfMjY4/MDAxNTAwMDEyMzM5NzY4.NBHHrYSqe6RuAUUxKm1IPwvuuoI_6nWV99lXiyUubWAg.jHOUOaZjAhQwcMpie8yfUaXV6rGMzPQ9WjWvLoTq5y4g.JPEG/15.jpg?type=w1200",
            "서대문구",
            "서대문빌딩",
            false,
            500,
            400000)
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        var bookmarkRecyclerView = binding.llMainBackdrop.rv_bookmark

        val bookmarkAdapter = BookmarkRecyclerViewAdapter(this, tempbookmarkvalue)
        bookmarkRecyclerView.adapter = bookmarkAdapter

        val lm = LinearLayoutManager(this)
        bookmarkRecyclerView.layoutManager = lm

        binding.ivSearchBar.setOnClickListener {
            startSearchFragment()
        }
    }

    fun startSearchFragment() {
        //TODO()
    }
}