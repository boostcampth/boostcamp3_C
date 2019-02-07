package kr.co.connect.boostcamp.livewhere.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.FragmentRecentSearchBinding
import kr.co.connect.boostcamp.livewhere.ui.main.adapter.RecentSearchRecyclerViewAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment() {

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: HomeActivityViewModel by sharedViewModel()
    private lateinit var binding : FragmentRecentSearchBinding
    private lateinit var recentsearchRecyclerviewAdapter: RecentSearchRecyclerViewAdapter
    private lateinit var recyclerViewLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recentsearchRecyclerviewAdapter = RecentSearchRecyclerViewAdapter(this@SearchFragment)
        recyclerViewLayoutManager = LinearLayoutManager(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRecentSearchBinding.inflate(inflater, container, false).apply {
            viewModel = this@SearchFragment.viewModel
            setLifecycleOwner(this@SearchFragment)
        }
    }
}