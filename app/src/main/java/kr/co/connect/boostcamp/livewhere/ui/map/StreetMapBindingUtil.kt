package kr.co.connect.boostcamp.livewhere.ui.map

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData

@BindingAdapter(value = ["onClickFinish"])
fun Toolbar.onClickFinish(isHomeClick: Boolean) {
    setNavigationOnClickListener {
        (context as StreetMapActivity).finish()
    }
}

@BindingAdapter(value = ["onTitleText"])
fun Toolbar.onTitleText(addressLiveData: LiveData<String>) {
    if (addressLiveData.value != null) {
        title = addressLiveData.value
    }
}