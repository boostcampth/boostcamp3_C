package kr.co.connect.boostcamp.livewhere.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityHomeBinding
import kr.co.connect.boostcamp.livewhere.ui.detail.DetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val HOME_CONTAINER_ID = R.id.fl_home_frame
    }

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        if (savedInstanceState == null) {
            addHomeFragment()
        }

        binding.apply {
            viewModel = this@HomeActivity.viewModel
            setLifecycleOwner(this@HomeActivity)
        }
    }

    private fun addHomeFragment(){
        val fragment = HomeFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(HOME_CONTAINER_ID, fragment)
            .commit()
    }

    private fun startSearchFragment() {
        val fragment = SearchFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(HOME_CONTAINER_ID, fragment)
            .commit()
    }

}