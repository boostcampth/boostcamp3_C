package kr.co.connect.boostcamp.livewhere.ui.main

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["isInitToolbar"])
fun Toolbar.initHomeToolbar(isReady: Boolean) {
    if (isReady) {
        (context as HomeActivity).setSupportActionBar(this)
    }
}

@BindingAdapter(value=["onStartBackFragment"])
fun Toolbar.setOnStartBackFragment(homeViewModel: HomeViewModel){
    setNavigationOnClickListener {
        homeViewModel.onClickedBack()
    }
}