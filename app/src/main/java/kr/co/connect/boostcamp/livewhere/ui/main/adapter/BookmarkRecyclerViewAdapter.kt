package kr.co.connect.boostcamp.livewhere.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kr.co.connect.boostcamp.livewhere.databinding.ItemBookmarkRecyclerViewBinding
import kr.co.connect.boostcamp.livewhere.model.Bookmark

class BookmarkRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner)
    : RecyclerView.Adapter<BookmarkRecyclerViewAdapter.BookmarkViewHolder>() {

    private var list = listOf<Bookmark>()

    fun setData(list: List<Bookmark>) {
        this.list = list
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = ItemBookmarkRecyclerViewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(lifecycleOwner, list[position])
    }

    inner class BookmarkViewHolder(
        private val itemBinding: ItemBookmarkRecyclerViewBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(lifecycleOwner: LifecycleOwner, bookmark: Bookmark) {
            itemBinding.setLifecycleOwner(lifecycleOwner)
            itemBinding.bookmark = bookmark

            if (itemBinding.ivBookmarkImage != null) {
                Glide.with(itemBinding.root)
                    .load(bookmark.imgUrl)
                    .apply { RequestOptions.fitCenterTransform() }
                    .into(itemBinding.ivBookmarkImage)
            }
        }
    }
}