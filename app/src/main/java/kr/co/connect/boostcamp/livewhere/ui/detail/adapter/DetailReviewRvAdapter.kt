package kr.co.connect.boostcamp.livewhere.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kr.co.connect.boostcamp.livewhere.databinding.FragmentDetailReviewMoreItemBinding
import kr.co.connect.boostcamp.livewhere.model.Review

class DetailReviewRvAdapter(
    private val lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<DetailReviewRvAdapter.DetailReviewViewHolder>() {

    private var list = listOf<Review>()

    fun setData(list: List<Review>){
        this.list = list
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailReviewViewHolder {
        val binding = FragmentDetailReviewMoreItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailReviewViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    override fun onBindViewHolder(holder: DetailReviewViewHolder, position: Int) {
        holder.bind(lifecycleOwner, list[position])
    }


    class DetailReviewViewHolder(
        private val itemBinding: FragmentDetailReviewMoreItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(lifecycleOwner: LifecycleOwner,review: Review) {
            itemBinding.lifecycleOwner = lifecycleOwner
            itemBinding.review = review
            itemBinding.executePendingBindings()
        }
    }
}

