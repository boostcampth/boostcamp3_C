package kr.co.connect.boostcamp.livewhere.ui.map

import android.util.Log
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import kr.co.connect.boostcamp.livewhere.util.MapHelperImpl
import kr.co.connect.boostcamp.livewhere.util.tMapKey

@BindingAdapter(value = ["createTMap"])
fun LinearLayout.initMap(mapHelper: MapHelperImpl){
    Log.d("TMAP_INFO","CREATE")
    val tMapView = mapHelper.create()
    Log.d("TMAP_INFO", tMapKey)
    tMapView.setSKTMapApiKey(tMapKey)
    this.addView(tMapView)
}