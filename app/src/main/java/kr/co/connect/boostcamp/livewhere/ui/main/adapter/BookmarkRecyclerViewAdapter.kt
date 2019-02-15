package kr.co.connect.boostcamp.livewhere.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
import kr.co.connect.boostcamp.livewhere.databinding.ItemBookmarkRecyclerViewBinding
import kr.co.connect.boostcamp.livewhere.ui.main.BookmarkViewModel

class BookmarkRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val bookmarkViewModel: BookmarkViewModel
) : RecyclerView.Adapter<BookmarkRecyclerViewAdapter.BookmarkViewHolder>() {

    private var list = listOf<BookmarkEntity>()

    fun setData(list: List<BookmarkEntity>) {
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
        holder.bind(lifecycleOwner, list[position], bookmarkViewModel)
    }

    inner class BookmarkViewHolder(
        private val itemBinding: ItemBookmarkRecyclerViewBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(lifecycleOwner: LifecycleOwner, bookmark: BookmarkEntity, bookmarkViewModel: BookmarkViewModel) {
            itemBinding.setLifecycleOwner(lifecycleOwner)
            //TODO: Databinding error
            itemBinding.tvBookmarkLocationContents.text = bookmark.address
            itemBinding.bookmark = bookmark
            itemBinding.bookmarkViewModel = bookmarkViewModel

            Glide.with(itemBinding.root)
                .load(bookmark.img_url)
                .apply { RequestOptions.fitCenterTransform() }
                .into(itemBinding.ivBookmarkImage)

        }
    }
}