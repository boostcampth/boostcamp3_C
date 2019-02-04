package kr.co.connect.boostcamp.livewhere.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kr.co.connect.boostcamp.livewhere.databinding.FragmentDetailPastTransactionBodyItemBinding
import kr.co.connect.boostcamp.livewhere.model.PastTransaction

class DetailRvAdapter(
    private val lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<DetailRvAdapter.DetailViewHolder>() {

    private var list = listOf<PastTransaction>()

    fun setData(list: List<PastTransaction>){
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
        fun bind(lifecycleOwner: LifecycleOwner,pastTransaction: PastTransaction) {
            itemBinding.setLifecycleOwner(lifecycleOwner)
            itemBinding.pastTransaction = pastTransaction
            itemBinding.executePendingBindings()
            /* FIXME apply 를 활용하면 효율적으로 코드량을 줄일 수 있습니다.
            itemBinding.apply {
                setLifecycleOwner(lifecycleOwner)
                pastTransaction = _pastTransaction
                executePendingBindings()
            }
            */
        }
    }
}

