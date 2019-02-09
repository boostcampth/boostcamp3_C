package kr.co.connect.boostcamp.livewhere.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityDetailBinding
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
        binding = DataBindingUtil.setContentView(this, kr.co.connect.boostcamp.livewhere.R.layout.activity_detail)

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

        viewModel.reviewPostOpenClicked.observe(this, Observer {
            addReviewPost()
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