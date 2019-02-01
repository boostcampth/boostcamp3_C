package kr.co.connect.boostcamp.livewhere.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.layout_main_back_drop.view.*
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
        Bookmark("https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/Doona_Bae_promoting_The_Tunnel.png/250px-Doona_Bae_promoting_The_Tunnel.png", "광진구", "광진빌딩", false, 0, 10000)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        var bookmarkRecyclerView = binding.llMainBackdrop.rvBookmark

        val bookmarkAdapter = BookmarkRecyclerViewAdapter(this, tempbookmarkvalue)
        bookmarkRecyclerView.adapter = bookmarkAdapter

        val lm = LinearLayoutManager(this)
        bookmarkRecyclerView.layoutManager = lm
    }
}