package kr.co.connect.boostcamp.livewhere.ui.map

import android.graphics.BitmapFactory
import android.widget.LinearLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.R

@BindingAdapter(value = ["isCreateTMap"])
fun LinearLayout.initMap(isCreateMap: Boolean) {
    if(isCreateMap){
        val tMapView = TMapView(this.context)

        tMapView.setSKTMapApiKey(BuildConfig.TmapApiKey)
        this.addView(tMapView)
    }
}


@BindingAdapter(value=["setOnLongClickListenerCallback"])
fun LinearLayout.setOnLongClickListenerCallback(mapViewModel:MapViewModel){
    val tMapView = getChildAt(0) as TMapView
    val context = tMapView.context
    tMapView.setOnLongClickListenerCallback(mapViewModel)
}

@BindingAdapter(value=["onDrawMarker","onPointLiveData"])
fun LinearLayout.onDrawMarker(mapViewModel: MapViewModel, pointLiveData: LiveData<TMapPoint>){
    val tMapView = getChildAt(0) as TMapView
    val markerBitmap = BitmapFactory.decodeResource(this.context.resources, R.drawable.ic_map_marker_24dp)
    if(pointLiveData.value!=null){
        val tMapMarkerItem = mapViewModel.mapUtilImpl.makeMarker(pointLiveData.value!!, markerBitmap)
        tMapView.addMarkerItem("marker", tMapMarkerItem)
    }
}

@BindingAdapter(value = ["triggerBackdrop", "triggerFloatingButton"])
fun FloatingActionButton.setTriggerBackdrop(backdropML: MotionLayout, filterML: MotionLayout) {
    this.setOnClickListener {
        if (backdropML.currentState == R.layout.motion_01_map_backdrop_end) {
            backdropML.transitionToStart()
        } else {
            filterML.transitionToStart()
        }
    }
}

@BindingAdapter(value = ["triggerFloatingButton"])
fun MotionLayout.setTriggerFB(filterML: MotionLayout) {
    this.setTransitionListener(object : MotionLayout.TransitionListener {
        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

        override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {
            if (currentId == R.layout.motion_01_map_backdrop_start) {
                filterML.transitionToStart()
            } else if (currentId == R.layout.motion_01_map_backdrop_end) {
                filterML.transitionToStart()
            }
        }
    })
}