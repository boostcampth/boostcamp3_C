package kr.co.connect.boostcamp.livewhere.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kr.co.connect.boostcamp.livewhere.databinding.FragmentDetailReviewMoreItemBinding
import kr.co.connect.boostcamp.livewhere.model.Review
import kr.co.connect.boostcamp.livewhere.ui.detail.DetailEventListener

class DetailReviewRvAdapter(
    private val lifecycleOwner: LifecycleOwner,private val eventListener: DetailEventListener,private val uuid:String) :
    RecyclerView.Adapter<DetailReviewRvAdapter.DetailReviewViewHolder>() {

    private var list = listOf<Review>()

    fun setData(list: List<Review>){
        this.list = list
        this.notifyItemRangeRemoved(0,list.size)
        this.notifyItemRangeInserted(0,list.size)
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
        holder.bind(lifecycleOwner, list[position],eventListener,uuid)
    }


    class DetailReviewViewHolder(
        private val itemBinding: FragmentDetailReviewMoreItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(lifecycleOwner: LifecycleOwner,review: Review,eventListener:DetailEventListener,uuid:String) {
            itemBinding.apply {  }
            itemBinding.lifecycleOwner = lifecycleOwner
            itemBinding.review = review
            itemBinding.eventListener = eventListener
            itemBinding.uuid = uuid
            setDeleteVisibility(uuid)
            itemBinding.executePendingBindings()
        }


        private fun setDeleteVisibility(uuid:String){
            if(itemBinding.review!!.id==uuid){
                itemBinding.reviewDeleteBtn.visibility = View.VISIBLE
            }else{
                itemBinding.reviewDeleteBtn.visibility = View.GONE
            }
        }
    }
}

