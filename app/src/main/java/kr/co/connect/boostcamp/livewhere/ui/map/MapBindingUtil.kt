package kr.co.connect.boostcamp.livewhere.ui.map

import android.widget.LinearLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.util.MapHelperImpl

@BindingAdapter(value = ["createTMap"])
fun LinearLayout.initMap(mapHelper: MapHelperImpl) {
    val tMapView = mapHelper.create()
    tMapView.setSKTMapApiKey(BuildConfig.TmapApiKey)
    this.addView(tMapView)
}

@BindingAdapter(value = ["triggerBackdrop","triggerFloatingButton"])
fun FloatingActionButton.setTriggerBackdrop(backdropML: MotionLayout, filterML:MotionLayout) {
    this.setOnClickListener {
        if(backdropML.currentState==R.layout.motion_01_map_backdrop_end){
            backdropML.transitionToStart()
        }else{
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