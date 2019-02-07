package kr.co.connect.boostcamp.livewhere.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kr.co.connect.boostcamp.livewhere.databinding.ItemRecentSearchRecyclerViewBinding
import kr.co.connect.boostcamp.livewhere.model.RecentSearch

class RecentSearchRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner)
    : RecyclerView.Adapter<RecentSearchRecyclerViewAdapter.RecentSearchViewHolder>() {

    private var list = listOf<RecentSearch>()

    fun setData(list: List<RecentSearch>) {
        this.list = list
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
        val binding = ItemRecentSearchRecyclerViewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentSearchViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
        //holder.bind(lifecycleOwner, list[position])
    }

    inner class RecentSearchViewHolder(
        private val itemBinding: ItemRecentSearchRecyclerViewBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(lifecycleOwner: LifecycleOwner, recentSearch: RecentSearch) {
            itemBinding.setLifecycleOwner(lifecycleOwner)
            itemBinding.recentSearch = recentSearch
        }
    }
}