package kr.co.connect.boostcamp.livewhere.ui.main

import android.util.Log
import android.view.KeyEvent
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.connect.boostcamp.livewhere.data.entity.RecentSearchEntity
import kr.co.connect.boostcamp.livewhere.ui.main.adapter.RecentSearchRecyclerViewAdapter

@BindingAdapter("setRecentRecyclerViewItems")
fun setRecentRecyclerViewItems(recyclerView: RecyclerView, itemList: List<RecentSearchEntity>?) {
    if (itemList != null) {
        (recyclerView.adapter as RecentSearchRecyclerViewAdapter).setData(itemList)
    } else {
        // TODO 데이터 정보 없음 처리.
    }
}

@BindingAdapter("searchDone")
fun finishEntering(editText: EditText, viewModel: SearchViewModel) {
    editText.setOnKeyListener { v, keyCode, event ->
        if((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            //After Enter Key
            viewModel.onFinishSearch(editText.text.toString())
            return@setOnKeyListener true
        } else {
            return@setOnKeyListener false
        }
    }
}