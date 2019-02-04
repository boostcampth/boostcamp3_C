package kr.co.connect.boostcamp.livewhere.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.connect.boostcamp.livewhere.databinding.FragmentDetailPastTransactionMoreBinding
import kr.co.connect.boostcamp.livewhere.ui.detail.adapter.DetailTransactionRvAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailFragmentMore : Fragment() {

    companion object {
        fun newInstance(): DetailFragmentMore {
            val args = Bundle()
            val fragment = DetailFragmentMore()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: DetailViewModel by sharedViewModel()
    private lateinit var binding: FragmentDetailPastTransactionMoreBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailPastTransactionMoreBinding.inflate(inflater,container,false).apply {
            viewModel = this@DetailFragmentMore.viewModel
            setLifecycleOwner(this@DetailFragmentMore)
        }

        binding.pastTransactionMoreRv.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = DetailTransactionRvAdapter(this@DetailFragmentMore)
        }

        return binding.root
    }



}