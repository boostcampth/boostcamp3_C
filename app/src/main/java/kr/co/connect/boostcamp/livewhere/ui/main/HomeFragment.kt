package kr.co.connect.boostcamp.livewhere.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_home_backdrop.view.*
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.FragmentHomeBinding
import kr.co.connect.boostcamp.livewhere.ui.main.adapter.BookmarkRecyclerViewAdapter
import org.checkerframework.framework.qual.Bottom
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {
    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val homeViewModel: HomeViewModel by sharedViewModel()
    private val bookmarkViewModel: BookmarkViewModel by sharedViewModel()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bookmarkRecyclerViewAdapter: BookmarkRecyclerViewAdapter
    private lateinit var recyclerViewLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookmarkRecyclerViewAdapter = BookmarkRecyclerViewAdapter(this@HomeFragment)
        recyclerViewLayoutManager = LinearLayoutManager(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            homeViewModel = this@HomeFragment.homeViewModel
            bookmarkViewModel = this@HomeFragment.bookmarkViewModel
            setLifecycleOwner(this@HomeFragment)
        }

        binding.clHomeBackdrop.rv_bookmark.apply {
            layoutManager = recyclerViewLayoutManager
            adapter = bookmarkRecyclerViewAdapter
        }

        observe()
        return binding.root
    }

    private fun observe() {
        homeViewModel.btnClicked.observe(this, Observer {
            val bottomSheetBehavior = BottomSheetBehavior.from(binding.clHomeBackdrop.ll_main_backdrop)
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                binding.clHomeBackdrop.ll_main_backdrop
                    .iv_backdrop_btn.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                binding.clHomeBackdrop.ll_main_backdrop
                    .iv_backdrop_btn.setImageResource(R.drawable.ic_arrow_up_black_24dp)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        })
    }
}