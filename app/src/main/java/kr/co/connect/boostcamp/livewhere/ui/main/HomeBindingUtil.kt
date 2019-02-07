package kr.co.connect.boostcamp.livewhere.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.connect.boostcamp.livewhere.model.Bookmark
import kr.co.connect.boostcamp.livewhere.ui.main.adapter.BookmarkRecyclerViewAdapter

@BindingAdapter("setRecyclerViewItems")
fun setRvItems(recyclerView: RecyclerView, itemList:List<Bookmark>?){
    if(itemList!=null){
        (recyclerView.adapter as BookmarkRecyclerViewAdapter).setData(itemList)
    }else{
        // TODO 데이터 정보 없음 처리.
    }
}