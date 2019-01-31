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
/*
@BindingAdapter("setTriggerMotion")
fun setTriggerMotion(filterML: MotionLayout,mapListener: MapListener){
    filterML.setTransitionListener(object : MotionLayout.TransitionListener{
        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onTransitionCompleted(ml: MotionLayout?, currentId: Int) {
            mapListener.onTransitionCompleted(ml,currentId)
        }

    })

}*/
/*
activityMapBinding.fbFilterCafe.setOnClickListener {
    activityMapBinding.mlBackdrop.transitionToStart()
}

activityMapBinding.mlBackdrop.setTransitionListener(object : MotionLayout.TransitionListener {
    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

    }

    override fun onTransitionStarted(p0: MotionLayout?, startId: Int, endId: Int) {
    }

    override fun onTransitionChange(p0: MotionLayout?, startId: Int, endId: Int, p3: Float) {
    }

    override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {
        if (currentId == R.layout.motion_01_map_backdrop_start) {
            activityMapBinding.mlFloatBtn.transitionToStart()
        } else if (currentId == R.layout.motion_01_map_backdrop_end) {
            activityMapBinding.mlFloatBtn.transitionToStart()
        }
    }
})*/