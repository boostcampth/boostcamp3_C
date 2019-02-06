package kr.co.connect.boostcamp.livewhere.ui.detail

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_detail_trend_price.view.*
import kr.co.connect.boostcamp.livewhere.LiveApplication
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityDetailBinding
import kr.co.connect.boostcamp.livewhere.ui.map.MapActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val DETAIL_CONTAINER_ID = R.id.detail_activity_container
    }

    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        if (savedInstanceState == null) {
            addDetailFragment()
        }

        binding.apply {
            viewModel = this@DetailActivity.viewModel
            setLifecycleOwner(this@DetailActivity)
        }

        viewModel.markerInfo.observe(this, Observer {
            viewModel.getCoordinateFromInfo()
            viewModel.getRecentPriceFromInfo()
            viewModel.getPastTransactionFromList()
        })

        viewModel.transactionMoreClicked.observe(this, Observer {
            addDetailMore()
        })

        viewModel.reviewMoreClicked.observe(this, Observer {
            addReviewMore()
        })

        viewModel.reviewPostClicked.observe(this, Observer {
            addReviewPost()
        })

        viewModel.avgPriceType.observe(this, Observer { //전세 월세별 시세추이
            setBarChart(binding.detailActivityCl.detail_fragment_chart,viewModel.getAvgPriceList())
        })
    }

    private fun addDetailFragment() {
        val fragment = DetailFragment.newInstance()
        currentFragment = fragment
        supportFragmentManager
            .beginTransaction()
            .replace(DETAIL_CONTAINER_ID, fragment)
            .commit()
    }

    private fun addDetailMore() {
        val fragment = DetailFragmentTransactionMore.newInstance()
        currentFragment = fragment
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_right,
                R.anim.slide_out_right
            )
            .replace(DETAIL_CONTAINER_ID, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun addReviewMore() {
        val fragment = DetailFragmentReviewMore.newInstance()
        currentFragment = fragment
        supportFragmentManager
            .beginTransaction()
//            .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_left,R.anim.slide_in_right,R.anim.slide_out_right)
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_right,
                R.anim.slide_out_right
            )
            .replace(DETAIL_CONTAINER_ID, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun addReviewPost() {
        val fragment = DetailFragmentReviewPost.newInstance()
        currentFragment = fragment
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_top,
                R.anim.slide_out_top,
                R.anim.slide_in_bottom,
                R.anim.slide_out_bottom
            )
            .replace(DETAIL_CONTAINER_ID, fragment)
            .addToBackStack(null)
            .commit()
    }

//    override fun onBackPressed() {
//        when (currentFragment) {
//            is DetailFragment -> super.onBackPressed()
//            is DetailFragmentTransactionMore -> supportFragmentManager.popBackStack()
//            is DetailFragmentReviewMore -> supportFragmentManager.popBackStack()
//            is DetailFragmentReviewPost -> supportFragmentManager.popBackStack()
//        }
//    }
}