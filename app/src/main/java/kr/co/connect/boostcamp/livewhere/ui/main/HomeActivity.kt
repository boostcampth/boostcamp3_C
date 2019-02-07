package kr.co.connect.boostcamp.livewhere.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val HOME_CONTAINER_ID = R.id.fl_home_frame
    }

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        if (savedInstanceState == null) {
            startHomeFragment()
        }

        binding.apply {
            viewModel = this@HomeActivity.viewModel
            searchViewModel = this@HomeActivity.searchViewModel
            setLifecycleOwner(this@HomeActivity)
        }

        viewModel.searchBtnClicked.observe(this, Observer {
            startSearchFragment()
        })

        searchViewModel.backBtnClicked.observe(this, Observer {
            startHomeFragment()
        })
    }

    private fun startHomeFragment(){
        Log.d("starthome", "started")
        currentFragment = HomeFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(HOME_CONTAINER_ID, currentFragment)
            .commit()
    }

    private fun startSearchFragment() {
        Log.d("startsearch", "started")
        currentFragment = SearchFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(HOME_CONTAINER_ID, currentFragment)
            .commit()
    }

}