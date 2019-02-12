package kr.co.connect.boostcamp.livewhere.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityHomeBinding
import kr.co.connect.boostcamp.livewhere.ui.map.MapActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val HOME_CONTAINER_ID = R.id.fl_home_frame
    }

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private val bookmarkViewModel: BookmarkViewModel by viewModel()
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        if (savedInstanceState == null) {
            startHomeFragment()
        }

        binding.apply {
            homeViewModel = this@HomeActivity.homeViewModel
            bookmarkViewModel = this@HomeActivity.bookmarkViewModel
            searchViewModel = this@HomeActivity.searchViewModel
            setLifecycleOwner(this@HomeActivity)
        }

        observeValues()
        initBookmark()
    }

    private fun initBookmark() {
        bookmarkViewModel.getBookmark()
    }

    private fun observeValues() {
        homeViewModel.searchBtnClicked.observe(this, Observer {
            startSearchFragment()
        })

        searchViewModel.backBtnClicked.observe(this, Observer {
            startHomeFragment()
        })

        searchViewModel.mapBtnClicked.observe(this, Observer {
            startMapActivity()
        })
    }

    private fun startHomeFragment(){
        currentFragment = HomeFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(HOME_CONTAINER_ID, currentFragment)
            .commit()
    }

    private fun startSearchFragment() {
        currentFragment = SearchFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(HOME_CONTAINER_ID, currentFragment)
            .commit()
    }

    private fun startMapActivity() {
        intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
        finish()
    }
}