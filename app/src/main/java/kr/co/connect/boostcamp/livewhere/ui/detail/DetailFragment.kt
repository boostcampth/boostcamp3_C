package kr.co.connect.boostcamp.livewhere.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_detail_past_transation.view.*
import kotlinx.android.synthetic.main.fragment_detail_trend_price.view.*
import kr.co.connect.boostcamp.livewhere.databinding.FragmentDetailBinding
import kr.co.connect.boostcamp.livewhere.ui.detail.adapter.DetailTransactionRvAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DetailFragment : Fragment() {

    companion object {
        fun newInstance(): DetailFragment {
            val args = Bundle()
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: DetailViewModel by sharedViewModel()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false).apply {
            viewModel = this@DetailFragment.viewModel
            setLifecycleOwner(this@DetailFragment)
        }

        binding.detailFragmentCl.detail_fragment_rv_past_transation.apply {
            layoutManager = LinearLayoutManager(context)
                    adapter = DetailTransactionRvAdapter(this@DetailFragment)
        }

        viewModel.avgPriceType.observe(this, Observer { //전세 월세별 시세추이
            setBarChart(binding.detailFragmentCl.detail_fragment_chart,viewModel.getAvgPriceList())
        })

        return binding.root
    }
}
