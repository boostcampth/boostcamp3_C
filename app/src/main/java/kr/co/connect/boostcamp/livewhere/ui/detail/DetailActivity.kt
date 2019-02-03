package kr.co.connect.boostcamp.livewhere.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity(){

    companion object {
        private const val DETAIL_CONTAINER_ID = R.id.detail_activity_container
    }

    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding : ActivityDetailBinding
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail)

        if (savedInstanceState == null) {
            addDetailFragment()
        }

        binding.apply {
            viewModel = this@DetailActivity.viewModel
            setLifecycleOwner(this@DetailActivity)
        }

        viewModel.transactionMoreClicked.observe(this, Observer {
            addDetailMore()
        })
    }

    private fun addDetailFragment(){
        val fragment = DetailFragment.newInstance()
        currentFragment = fragment
        supportFragmentManager
            .beginTransaction()
            .replace(DETAIL_CONTAINER_ID, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun addDetailMore(){
        val fragment = DetailFragmentMore.newInstance()
        currentFragment = fragment
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_left,R.anim.slide_in_right,R.anim.slide_out_right)
            .replace(DETAIL_CONTAINER_ID, fragment)
            .addToBackStack(null)
            .commit() //TODO 더보기 화면에서 나올때 기존 디테일화면으로 전환하도록 구현
    }

    override fun onBackPressed() {
        when (currentFragment) {
            is DetailFragment -> super.onBackPressed()
            is DetailFragmentMore -> supportFragmentManager.popBackStack()
        }
    }

}