package kr.co.connect.boostcamp.livewhere.ui.main.adapter

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

class AutoCompleteRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner
): RecyclerView.Adapter<AutoCompleteRecyclerViewAdapter.AutoCompleteViewHolder>() {
    private var list = listOf<String>()

    fun setData(list: List<String>) {
        this.list = list
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AutoCompleteViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: AutoCompleteViewHolder, position: Int) {
        holder.bind(lifecycleOwner, list[position])
    }

    inner class AutoCompleteViewHolder(
        private val itemBinding: ItemAutoCompleteRecyclerViewBinding
    ): RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(lifecycleOwner: LifecycleOwner, text: String) {
            itemBinding.setLifecycleOwner(lifecycleOwner)
            itemBinding.autoComplete = text
        }
    }
}