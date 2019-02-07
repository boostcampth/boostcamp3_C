package kr.co.connect.boostcamp.livewhere.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_detail_review_more.view.*
import kr.co.connect.boostcamp.livewhere.databinding.FragmentDetailPastTransactionMoreBinding
import kr.co.connect.boostcamp.livewhere.ui.detail.adapter.DetailTransactionRvAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailFragmentTransactionMore : Fragment() {

    companion object {
        fun newInstance(): DetailFragmentTransactionMore {
            val args = Bundle()
            val fragment = DetailFragmentTransactionMore()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: DetailViewModel by sharedViewModel()
    private lateinit var binding: FragmentDetailPastTransactionMoreBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailPastTransactionMoreBinding.inflate(inflater, container, false).apply {
            viewModel = this@DetailFragmentTransactionMore.viewModel
            setLifecycleOwner(this@DetailFragmentTransactionMore)
        }

        binding.pastTransactionMoreRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = DetailTransactionRvAdapter(this@DetailFragmentTransactionMore)
        }

        viewModel.pastTransactionSort.observe(this, Observer {
            viewModel.sortTransactionList()
            binding.pastTransactionMoreRv.adapter!!.notifyDataSetChanged()
        })

        return binding.root
    }


}