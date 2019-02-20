package kr.co.connect.boostcamp.livewhere.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.connect.boostcamp.livewhere.data.SharedPreferenceStorage
import kr.co.connect.boostcamp.livewhere.databinding.FragmentDetailReviewMoreBinding
import kr.co.connect.boostcamp.livewhere.model.Review
import kr.co.connect.boostcamp.livewhere.ui.detail.adapter.DetailReviewRvAdapter
import kr.co.connect.boostcamp.livewhere.util.DIALOG_MESSAGE
import kr.co.connect.boostcamp.livewhere.util.DIALOG_NEGATIVE_BUTTON
import kr.co.connect.boostcamp.livewhere.util.DIALOG_POSITIVE_BUTTON
import kr.co.connect.boostcamp.livewhere.util.DIALOG_TITLE
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

    private val pref by lazy{ SharedPreferenceStorage(context!!)}
    private val viewModel: DetailViewModel by sharedViewModel()
    private lateinit var binding: FragmentDetailReviewMoreBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailReviewMoreBinding.inflate(inflater,container,false).apply {
            viewModel = this@DetailFragmentReviewMore.viewModel
            lifecycleOwner = this@DetailFragmentReviewMore
        }

        binding.detailReviewMoreRv.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = DetailReviewRvAdapter(this@DetailFragmentReviewMore,this@DetailFragmentReviewMore.viewModel,pref.uuid!!)
        }

        viewModel.getComments().observe(this, Observer {
            binding.detailReviewMoreRv.adapter!!.notifyDataSetChanged()
        })

        viewModel.reviewDeleteSuccess.observe(this, Observer {
            viewModel.loadComments(viewModel.pnuCode.get()!!)
        })

        viewModel.reviewDeletePressed.observe(this, Observer {
            alertDeleteDialog(it)
        })

        return binding.root
    }

    private fun alertDeleteDialog(item: Review){
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(DIALOG_TITLE)
        builder.setMessage(DIALOG_MESSAGE)
        builder.setPositiveButton(DIALOG_POSITIVE_BUTTON) { _, _ ->
            viewModel.deleteComment(item)
        }
        builder.setNegativeButton(DIALOG_NEGATIVE_BUTTON, null)
        builder.create().show()
    }

}