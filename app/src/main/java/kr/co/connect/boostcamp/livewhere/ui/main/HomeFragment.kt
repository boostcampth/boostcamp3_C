package kr.co.connect.boostcamp.livewhere.ui.main

import android.content.Context
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
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bookmarkRecyclerViewAdapter: BookmarkRecyclerViewAdapter
    private lateinit var recyclerViewLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookmarkRecyclerViewAdapter = BookmarkRecyclerViewAdapter(this@HomeFragment, homeViewModel)
        recyclerViewLayoutManager = LinearLayoutManager(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            homeViewModel = this@HomeFragment.homeViewModel
            lifecycleOwner = this@HomeFragment
        }

        binding.clHomeBackdrop.rv_bookmark.apply {
            layoutManager = recyclerViewLayoutManager
            adapter = bookmarkRecyclerViewAdapter
        }

        observeBottomSheet()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        homeViewModel.bookmarkEntity.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                binding.clHomeBackdrop.tv_bookmark_empty.visibility = View.GONE
                binding.clHomeBackdrop.rv_bookmark.visibility = View.VISIBLE
            } else {
                binding.clHomeBackdrop.tv_bookmark_empty.visibility = View.VISIBLE
                binding.clHomeBackdrop.rv_bookmark.visibility = View.GONE
            }
        })
    }

    private fun observeBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.clHomeBackdrop.ll_main_backdrop)
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.clHomeBackdrop.ll_main_backdrop
                        .iv_backdrop_btn.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp)
                    homeViewModel.getBookmark()
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.clHomeBackdrop.ll_main_backdrop
                        .iv_backdrop_btn.setImageResource(R.drawable.ic_arrow_up_black_24dp)
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
        homeViewModel.btnClicked.observe(this, Observer {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        })
    }
}