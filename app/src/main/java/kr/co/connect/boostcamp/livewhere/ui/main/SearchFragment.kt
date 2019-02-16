package kr.co.connect.boostcamp.livewhere.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.libraries.places.api.net.PlacesClient
import io.fabric.sdk.android.services.common.CommonUtils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_recent_search.*
import kotlinx.android.synthetic.main.fragment_recent_search.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kr.co.connect.boostcamp.livewhere.databinding.FragmentSearchBinding
import kr.co.connect.boostcamp.livewhere.ui.main.adapter.AutoCompleteRecyclerViewAdapter
import kr.co.connect.boostcamp.livewhere.ui.main.adapter.RecentSearchRecyclerViewAdapter
import kr.co.connect.boostcamp.livewhere.util.DELETE_RECENT_SEARCH
import kr.co.connect.boostcamp.livewhere.util.EMPTY_STRING_TEXT
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
    private lateinit var autoCompleteRecyclerViewAdapter: AutoCompleteRecyclerViewAdapter
    private lateinit var recyclerViewLayoutManager: LinearLayoutManager
    private lateinit var autoCompleteLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recentSearchRecyclerViewAdapter = RecentSearchRecyclerViewAdapter(this@SearchFragment, searchViewModel)
        autoCompleteRecyclerViewAdapter = AutoCompleteRecyclerViewAdapter(this@SearchFragment, searchViewModel)
        autoCompleteLayoutManager = LinearLayoutManager(context)
        recyclerViewLayoutManager = LinearLayoutManager(context)

        searchViewModel.isRecentSearchVisible.observe(this, Observer {
            changeSearchRv(it)
        })
        searchViewModel.recentSearch.observe(this, Observer {
            if(!it.isNullOrEmpty()) {
                binding.llSearchFragment.tv_recent_search_empty.visibility = View.GONE
                binding.llSearchFragment.rv_recent_search.visibility = View.VISIBLE
            }
            else {
                binding.llSearchFragment.tv_recent_search_empty.visibility = View.VISIBLE
                binding.llSearchFragment.rv_recent_search.visibility = View.GONE
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            searchViewModel = this@SearchFragment.searchViewModel
            lifecycleOwner = this@SearchFragment
        }

        binding.svSearch.rv_recent_search.apply {
            layoutManager = recyclerViewLayoutManager
            adapter = recentSearchRecyclerViewAdapter
        }

        binding.rvAutoComplete.apply {
            layoutManager = autoCompleteLayoutManager
            adapter = autoCompleteRecyclerViewAdapter
        }

        setClient()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        searchViewModel.setVisibility()

        startObserve()
    }

    private fun startObserve() {
        searchViewModel.backBtnClicked.observe(this, Observer {
            hideKeyboard()
            (activity as HomeActivity).startHomeFragment()
        })

        searchViewModel.mapBtnClicked.observe(this, Observer {
            (activity as HomeActivity).startMapActivity()
        })

        searchViewModel.searchText.observe(this, Observer {
            if(searchViewModel.searchText.value.isNullOrEmpty()) {
                Toast.makeText(activity as HomeActivity, EMPTY_STRING_TEXT, Toast.LENGTH_LONG).show()
            } else {
                if(binding.etSearchBar.text.toString() == searchViewModel.searchText.toString()) {
                    if(searchViewModel.autoCompleteList.value.isNullOrEmpty()) {
                        (activity as HomeActivity).startMapActivity(searchViewModel.autoCompleteList.value!![0])
                    }
                } else {
                    (activity as HomeActivity).startMapActivity(searchViewModel.searchText.value)
                }
            }
        })

        searchViewModel.showToast.observe(this, Observer {
            if(it) {
                Toast.makeText(activity as HomeActivity, DELETE_RECENT_SEARCH, Toast.LENGTH_LONG).show()
                searchViewModel.setToastDone()
            }
        })
    }

    private fun hideKeyboard() {
        binding.etSearchBar.inputType = 0
        val imm = (activity as HomeActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearchBar.windowToken, 0)
    }

    private fun setClient() {
        searchViewModel.setClient((activity as HomeActivity).placesClient)
    }

    private fun changeSearchRv(set: Boolean) {
        if (set) {
            binding.rvAutoComplete.visibility = View.GONE
            binding.svSearch.ll_recent_search.visibility = View.VISIBLE
        } else {
            binding.rvAutoComplete.visibility = View.VISIBLE
            binding.svSearch.ll_recent_search.visibility = View.GONE
        }
    }
}