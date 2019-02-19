package kr.co.connect.boostcamp.livewhere.ui.main

import android.view.KeyEvent
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.focusChanges
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.disposables.Disposable
import kr.co.connect.boostcamp.livewhere.data.entity.RecentSearchEntity
import kr.co.connect.boostcamp.livewhere.ui.main.adapter.AutoCompleteRecyclerViewAdapter
import kr.co.connect.boostcamp.livewhere.ui.main.adapter.RecentSearchRecyclerViewAdapter
import java.util.concurrent.TimeUnit

@BindingAdapter("setRecentRecyclerViewItems")
fun setRecentRecyclerViewItems(recyclerView: RecyclerView, itemList: List<RecentSearchEntity>?) {
    if (!itemList.isNullOrEmpty()) {
        (recyclerView.adapter as RecentSearchRecyclerViewAdapter).setData(itemList)
    } else {
        // TODO 데이터 정보 없음 처리.
    }
}

@BindingAdapter("setAutoCompleteRecyclerViewItems")
fun setAutoCompleteRecyclerViewItems(recyclerView: RecyclerView, itemList: List<String>?) {
    if (!itemList.isNullOrEmpty()) {
        (recyclerView.adapter as AutoCompleteRecyclerViewAdapter).setData(itemList)
    } else {
        //TODO: 데이터 정보 없음 처리.
    }
}

/*@BindingAdapter("onClickDone")
fun hideKeyboard(editText: EditText, viewModel: HomeViewModel) {
    editText.setOnKeyListener { _, Keycode, event ->
        if ((event.action == KeyEvent.ACTION_DOWN) && (Keycode == KeyEvent.KEYCODE_ENTER)) {
            viewModel.setHideKeyboard(true)
            return@setOnKeyListener true
        } else {
            return@setOnKeyListener true
        }
    }
}*/

@BindingAdapter("handleFocus")
fun handleFocus(editText: EditText, viewModel: HomeViewModel): Disposable {
    return editText.focusChanges().subscribe {
        viewModel.setHideKeyboard(!it)
    }
}


@BindingAdapter("autoComplete")
fun autoComplete(editText: EditText, viewModel: HomeViewModel): Disposable {
    return editText.textChangeEvents()
        .debounce(500, TimeUnit.MILLISECONDS)
        .subscribe {
            viewModel.getTmapApi(it.text.toString())
        }
}