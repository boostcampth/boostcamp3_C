package kr.co.connect.boostcamp.livewhere.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import kr.co.connect.boostcamp.livewhere.R


class BalloonOverlayView(context: Context, labelName: String, id: String) : FrameLayout(context) {

    private val layout: LinearLayout
    private var title: TextView? = null
    private var subTitle: TextView? = null

    init {
        setPadding(10, 0, 10, 0)
        layout = LinearLayout(context)
        layout.visibility = View.VISIBLE

        setupView(context, layout, labelName, id)

        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.bottomMargin = 138//중앙으로부터 높이
        params.gravity = Gravity.NO_GRAVITY
        addView(layout, params)
    }


    private fun setupView(context: Context, parent: ViewGroup, labelName: String, id: String) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.windows_map_info, parent, true)
        title = view.findViewById(R.id.tv_windows_title)
        subTitle = view.findViewById(R.id.tv_windows_subtitle)
        setTitle(labelName)
        setSubTitle(id)

    }

    fun setTitle(str: String) {
        title!!.text = str
    }

    fun setSubTitle(str: String) {
        subTitle!!.text = str
    }
}