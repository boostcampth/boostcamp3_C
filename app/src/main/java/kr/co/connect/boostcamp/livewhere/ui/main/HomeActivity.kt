package kr.co.connect.boostcamp.livewhere.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.android.synthetic.main.fragment_search.*
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityHomeBinding
import kr.co.connect.boostcamp.livewhere.ui.map.MapActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val HOME_CONTAINER_ID = R.id.fl_home_frame
    }

    private val SEARCH_TAG = "SEARCH_RESULT"

    private lateinit var binding: ActivityHomeBinding
    private lateinit var placesClient: PlacesClient
    private val homeViewModel: HomeViewModel by viewModel()
    private val bookmarkViewModel: BookmarkViewModel by viewModel()
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        Places.initialize(applicationContext, BuildConfig.GooglePlacesApiKey)
        placesClient = Places.createClient(this)

        if (savedInstanceState == null) {
            startHomeFragment()
        }

        binding.apply {
            homeViewModel = this@HomeActivity.homeViewModel
            bookmarkViewModel = this@HomeActivity.bookmarkViewModel
            searchViewModel = this@HomeActivity.searchViewModel
            setLifecycleOwner(this@HomeActivity)
        }

        setGoogleClient()
        observeValues()
        initBookmark()
    }

    private fun setGoogleClient() {
        searchViewModel.setClient(placesClient)
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

        searchViewModel.searchText.observe(this, Observer {
            startMapActivity(searchViewModel.searchText.value)
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
    }

    private fun startMapActivity(text: String?) {
        if(text != null) {
            intent = Intent(this, MapActivity::class.java)
            intent.putExtra(SEARCH_TAG, text)
            startActivity(intent)
        } else {
            startMapActivity()
        }
    }
}