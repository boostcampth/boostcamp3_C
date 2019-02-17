package kr.co.connect.boostcamp.livewhere.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
import kr.co.connect.boostcamp.livewhere.databinding.ItemBookmarkRecyclerViewBinding
import kr.co.connect.boostcamp.livewhere.ui.main.HomeViewModel

class BookmarkRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val homeViewModel: HomeViewModel
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
        holder.bind(lifecycleOwner, list[position], homeViewModel)
    }

    inner class BookmarkViewHolder(
        private val itemBinding: ItemBookmarkRecyclerViewBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(lifecycleOwner: LifecycleOwner, bookmark: BookmarkEntity, homeViewModel: HomeViewModel) {
            itemBinding.setLifecycleOwner(lifecycleOwner)
            itemBinding.tvBookmarkLocationContents.text = bookmark.address
            itemBinding.bookmark = bookmark
            itemBinding.homeViewModel = homeViewModel

            Glide.with(itemBinding.root)
                .load(itemBinding.root.context.getString(R.string.glide_street_img_url,bookmark.img_url,BuildConfig.GoogleApiKey))
                .apply { RequestOptions.fitCenterTransform() }
                .into(itemBinding.ivBookmarkImage)
        }
    }
}