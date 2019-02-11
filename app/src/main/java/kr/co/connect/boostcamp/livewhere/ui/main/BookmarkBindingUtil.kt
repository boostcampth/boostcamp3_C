package kr.co.connect.boostcamp.livewhere.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
import kr.co.connect.boostcamp.livewhere.ui.main.adapter.BookmarkRecyclerViewAdapter

@BindingAdapter("setBookmarkItems")
fun setBookmarkItems(view: RecyclerView, items: List<BookmarkEntity>) {
    view.adapter?.run {
        if(this is BookmarkRecyclerViewAdapter) this.setData(items)
    } ?.run {
        //TODO: NULL Exception
    }
}