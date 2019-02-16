package kr.co.connect.boostcamp.livewhere.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityHomeBinding
import kr.co.connect.boostcamp.livewhere.ui.map.MapActivity
import kr.co.connect.boostcamp.livewhere.util.APPLICATION_EXIT
import kr.co.connect.boostcamp.livewhere.util.SEARCH_TAG
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val HOME_CONTAINER_ID = R.id.fl_home_frame
    }

    private lateinit var binding: ActivityHomeBinding
    lateinit var placesClient: PlacesClient
    private val homeViewModel: HomeViewModel by viewModel()
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
            lifecycleOwner = this@HomeActivity
        }

        observeValues()
        initBookmark()
    }

    private fun initBookmark() {
        homeViewModel.getBookmark()
    }

    private fun observeValues() {
        homeViewModel.searchBtnClicked.observe(this, Observer {
            startSearchFragment()
        })

        homeViewModel.sendAddress.observe(this, Observer {
            startMapActivity(homeViewModel.sendAddress.value)
        })
    }

    fun startHomeFragment(){
        currentFragment = HomeFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_right,
                R.anim.slide_in_left,
                R.anim.slide_out_left
            )
            .replace(HOME_CONTAINER_ID, currentFragment)
            .commit()
    }

    private fun startSearchFragment() {
        currentFragment = SearchFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_right,
                R.anim.slide_out_right
            )
            .replace(HOME_CONTAINER_ID, currentFragment)
            .addToBackStack(null)
            .commit()
    }

    fun startMapActivity() {
        intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }

    fun startMapActivity(text: String?) {
        if(text != null) {
            intent = Intent(this, MapActivity::class.java)
            intent.putExtra(SEARCH_TAG, text)
            startActivity(intent)
        } else {
            startMapActivity()
        }
    }

    override fun onBackPressed() {
        val toast = Toast.makeText(this, APPLICATION_EXIT, Toast.LENGTH_LONG)
        if(currentFragment is SearchFragment) {
            startHomeFragment()
        } else {
            if(homeViewModel.onBackPressed()) {
                toast.show()
            } else {
                finish()
                toast.cancel()
            }
        }
    }
}