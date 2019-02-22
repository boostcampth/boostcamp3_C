package kr.co.connect.boostcamp.livewhere.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityHomeBinding
import kr.co.connect.boostcamp.livewhere.ui.map.MapActivity
import kr.co.connect.boostcamp.livewhere.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {

    companion object {
        private const val HOME_CONTAINER_ID = R.id.fl_home_frame
    }

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        if (savedInstanceState == null) {
            startNewFragment()
        }

        binding.apply {
            homeViewModel = this@HomeActivity.homeViewModel
            lifecycleOwner = this@HomeActivity
        }

        observeValues()

    }

    override fun onDestroy() {
        super.onDestroy()
        keyboardHide()
    }

    private fun observeValues() {
        homeViewModel.searchBtnClicked.observe(this, Observer {
            startSearchFragment()
        })

        homeViewModel.backBtnClicked.observe(this, Observer {
            keyboardHide()
            startHomeFragment()
        })

        homeViewModel.mapBtnClicked.observe(this, Observer {
            startMapActivity()
        })

        homeViewModel.searchMap.observe(this, Observer {
            if (homeViewModel.searchMap.value.isNullOrEmpty()) {
                Toast.makeText(this, EMPTY_STRING_TEXT, Toast.LENGTH_LONG).show()
            } else {
                startMapActivity(it)
            }
        })

        homeViewModel.showToast.observe(this, Observer {
            if (it) {
                Toast.makeText(this, DELETE_RECENT_SEARCH, Toast.LENGTH_LONG).show()
                homeViewModel.setToastDone()
            }
        })

        homeViewModel.bookmarkMap.observe(this, Observer {
            startMapActivity(it)
        })

        homeViewModel.hideKeyboard.observe(this, Observer {
            if (homeViewModel.hideKeyboard.value!!) {
                keyboardHide()
                homeViewModel.setHideKeyboard(false)
            }
        })
    }

    private fun startNewFragment() {
        currentFragment = HomeFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(HOME_CONTAINER_ID, currentFragment)
            .commit()
        homeViewModel.getBookmark()
    }

    private fun startHomeFragment() {
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
        homeViewModel.getBookmark()
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
        keyboardShow()
        homeViewModel.getRecentSearch()
    }

    private fun startMapActivity() {
        intent = Intent(this, MapActivity::class.java)
        keyboardHide()
        startActivity(intent)
    }

    private fun startMapActivity(map: HashMap<String, String>) {
        intent = Intent(this, MapActivity::class.java)
        intent.putExtra(LAT, map[LAT])
        intent.putExtra(LON, map[LON])
        keyboardHide()
        startActivity(intent)
    }

    override fun onBackPressed() {
        val toast = Toast.makeText(this, APPLICATION_EXIT, Toast.LENGTH_LONG)
        if (currentFragment is SearchFragment) {
            startHomeFragment()
        } else {
            if (homeViewModel.onBackPressed()) {
                toast.show()
            } else {
                finish()
                toast.cancel()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(kr.co.connect.boostcamp.livewhere.R.menu.menu_home, menu)
        return true
    }

    //추가된 소스, ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_term_of_use -> {
                currentFragment = PolicyFragment.newInstance()
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
                keyboardHide()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}