package kr.co.connect.boostcamp.livewhere.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kr.co.connect.boostcamp.livewhere.databinding.FragmentDetailPastTransactionBodyItemBinding
import kr.co.connect.boostcamp.livewhere.model.PastTransaction

class DetailTransactionRvAdapter(
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<DetailTransactionRvAdapter.DetailViewHolder>() {

    private var list = listOf<PastTransaction>()

    fun setData(list: List<PastTransaction>) {
        this.list = list
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = FragmentDetailPastTransactionBodyItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(lifecycleOwner, list[position])
    }


    class DetailViewHolder(
        private val itemBinding: FragmentDetailPastTransactionBodyItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(_lifecycleOwner: LifecycleOwner, _pastTransaction: PastTransaction) {
            itemBinding.apply {
                lifecycleOwner = _lifecycleOwner
                pastTransaction = _pastTransaction
                executePendingBindings()
            }
        }
    }
}

