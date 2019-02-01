package kr.co.connect.boostcamp.livewhere.ui.map

import androidx.constraintlayout.motion.widget.MotionLayout

interface MapListener{
    fun  onTransitionCompleted(ml: MotionLayout?, currentId: Int)
}