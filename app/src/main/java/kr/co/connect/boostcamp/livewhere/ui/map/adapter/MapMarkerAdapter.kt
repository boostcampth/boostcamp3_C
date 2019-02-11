package kr.co.connect.boostcamp.livewhere.ui.map.adapter

import android.content.Context
import android.view.View
import com.naver.maps.map.overlay.InfoWindow
import kotlinx.android.synthetic.main.dialog_window_map_info.view.*
import kr.co.connect.boostcamp.livewhere.R

//마커 위의 글씨를 보여주는 info
class MapMarkerAdapter(private val mContext: Context, private val title: String) :
    InfoWindow.DefaultViewAdapter(mContext) {
    override fun getContentView(p0: InfoWindow): View {
        val view = View.inflate(mContext, R.layout.dialog_window_map_info, null)
        view.tv_content.text = title
        return view
    }
}