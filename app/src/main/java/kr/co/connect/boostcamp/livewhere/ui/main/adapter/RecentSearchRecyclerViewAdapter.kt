package kr.co.connect.boostcamp.livewhere.ui.main.adapter

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kr.co.connect.boostcamp.livewhere.model.RecentSearch

class RecentSearchRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner)
    : RecyclerView.Adapter<RecentSearchRecyclerViewAdapter.RecentSearchAdapter>() {

    private var list = listOf<RecentSearch>()

    fun setData(list: List<RecentSearch>) {
        this.list = list
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentSearchAdapter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class RecentSearchAdapter(
        private val itemBinding: ItemRecentSearchRecyclerviewBinding
    ) : RecyclerView.ViewHolder(itembinding.root) {

        fun bind(lifecycleOwner: LifecycleOwner, recentSearch: RecentSearch) {
            itemBinding.setLifecycleOwner(lifecycleOwner)
            itemBinding.re
        }
    }
}