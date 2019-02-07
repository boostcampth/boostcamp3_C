package kr.co.connect.boostcamp.livewhere.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.connect.boostcamp.livewhere.databinding.FragmentDetailReviewMoreBinding
import kr.co.connect.boostcamp.livewhere.ui.detail.adapter.DetailReviewRvAdapter
import kr.co.connect.boostcamp.livewhere.ui.detail.adapter.DetailTransactionRvAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailFragmentReviewMore : Fragment() {

    companion object {
        fun newInstance(): DetailFragmentReviewMore {
            val args = Bundle()
            val fragment = DetailFragmentReviewMore()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: DetailViewModel by sharedViewModel()
    private lateinit var binding: FragmentDetailReviewMoreBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailReviewMoreBinding.inflate(inflater,container,false).apply {
            viewModel = this@DetailFragmentReviewMore.viewModel
            setLifecycleOwner(this@DetailFragmentReviewMore)
        }

        binding.detailReviewMoreRv.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = DetailReviewRvAdapter(this@DetailFragmentReviewMore)
        }

        return binding.root
    }

}