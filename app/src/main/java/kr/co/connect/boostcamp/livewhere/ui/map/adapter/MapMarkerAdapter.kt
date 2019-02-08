package kr.co.connect.boostcamp.livewhere.ui.map.adapter

import android.content.Context
import android.view.View
import com.naver.maps.map.overlay.InfoWindow
import kotlinx.android.synthetic.main.dialog_window_map_info.view.*
import kr.co.connect.boostcamp.livewhere.R


//TODO: info는 그래픽으로 사용되기 때문에, 추후에 해당 어댑터를 삭제하고 recyclerView를 하단에 둘 것입니다.
class MapMarkerAdapter(
    private val mContext: Context,
    private val title: String
) :
    InfoWindow.DefaultViewAdapter(mContext) {
    override fun getContentView(p0: InfoWindow): View {
        val view = View.inflate(mContext, R.layout.dialog_window_map_info, null)
        view.tv_content.text = title
        return view
    }


}