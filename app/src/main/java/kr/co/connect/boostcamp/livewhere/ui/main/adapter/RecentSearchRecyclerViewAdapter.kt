package kr.co.connect.boostcamp.livewhere.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kr.co.connect.boostcamp.livewhere.data.entity.RecentSearchEntity
import kr.co.connect.boostcamp.livewhere.databinding.ItemRecentSearchRecyclerViewBinding
import kr.co.connect.boostcamp.livewhere.ui.main.SearchViewModel

class RecentSearchRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val searchViewModel: SearchViewModel
) : RecyclerView.Adapter<RecentSearchRecyclerViewAdapter.RecentSearchViewHolder>() {

    private var list = listOf<RecentSearchEntity>()

    fun setData(list: List<RecentSearchEntity>) {
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
        holder.bind(lifecycleOwner, list[position], searchViewModel)
    }

    inner class RecentSearchViewHolder(
        private val itemBinding: ItemRecentSearchRecyclerViewBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(lifecycleOwner: LifecycleOwner, recentSearch: RecentSearchEntity, searchViewModel: SearchViewModel) {
            itemBinding.lifecycleOwner = lifecycleOwner
            itemBinding.searchViewModel = searchViewModel
            itemBinding.recentSearch = recentSearch
        }
    }
}