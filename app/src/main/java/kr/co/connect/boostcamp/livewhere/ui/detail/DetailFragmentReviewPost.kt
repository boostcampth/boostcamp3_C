package kr.co.connect.boostcamp.livewhere.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kr.co.connect.boostcamp.livewhere.databinding.FragmentDetailPostReviewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DetailFragmentReviewPost : Fragment() {

    companion object {
        fun newInstance(): DetailFragmentReviewPost {
            val args = Bundle()
            val fragment = DetailFragmentReviewPost()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: DetailViewModel by sharedViewModel()
    private lateinit var binding: FragmentDetailPostReviewBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailPostReviewBinding.inflate(inflater, container, false).apply {
            viewModel = this@DetailFragmentReviewPost.viewModel
            lifecycleOwner = this@DetailFragmentReviewPost
        }

        binding.postReviewButton.requestFocus()

        viewModel.reviewPostClicked.observe(this, Observer {
            viewModel.postComment()
        })

        return binding.root
    }

}

