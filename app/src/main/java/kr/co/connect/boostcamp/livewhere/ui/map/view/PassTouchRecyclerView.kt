package kr.co.connect.boostcamp.livewhere.ui.map.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class PassTouchRecyclerView(context: Context, attributeSet: AttributeSet? = null) :
    RecyclerView(context, attributeSet) {
    private var isNotPassTouch = false

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(isNotPassTouch)
        {
            super.onTouchEvent(event)
        }
        return isNotPassTouch
    }

    fun setNotTouch(flag: Boolean) {
        isNotPassTouch = flag
    }
}