package kr.co.connect.boostcamp.livewhere.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recent_search.view.*
import kr.co.connect.boostcamp.livewhere.databinding.FragmentSearchBinding
import kr.co.connect.boostcamp.livewhere.ui.main.adapter.RecentSearchRecyclerViewAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment() {

    companion object {
        fun newInstance(): SearchFragment {
            val args = Bundle()
            val fragment = SearchFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val searchViewModel: SearchViewModel by sharedViewModel()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var recentSearchRecyclerViewAdapter: RecentSearchRecyclerViewAdapter
    private lateinit var recyclerViewLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recentSearchRecyclerViewAdapter = RecentSearchRecyclerViewAdapter(this@SearchFragment)
        recyclerViewLayoutManager = LinearLayoutManager(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            searchViewModel = this@SearchFragment.searchViewModel
            setLifecycleOwner(this@SearchFragment)
        }

        binding.svSearch.rv_recent_search.apply {
            layoutManager = recyclerViewLayoutManager
            adapter = recentSearchRecyclerViewAdapter
        }

        return binding.root
    }
}