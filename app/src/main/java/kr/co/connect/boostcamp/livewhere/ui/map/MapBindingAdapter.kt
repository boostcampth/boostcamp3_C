package kr.co.connect.boostcamp.livewhere.ui.map

import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.util.MapHelperImpl

@BindingAdapter(value = ["createTMap"])
fun LinearLayout.initMap(mapHelper: MapHelperImpl){
    val tMapView = mapHelper.create()
    tMapView.setSKTMapApiKey(BuildConfig.TmapApiKey)
    this.addView(tMapView)
}