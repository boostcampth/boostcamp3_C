package kr.co.connect.boostcamp.livewhere.ui.map

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.naver.maps.map.overlay.InfoWindow
import kotlinx.android.synthetic.main.dialog_window_map_inifo.view.*
import kr.co.connect.boostcamp.livewhere.R


class MapMarkerAdapter(
    private val mContext: Context,
    private val parent: ViewParent,
    private val imgUrl: String,
    private val title: String
) :
    InfoWindow.DefaultViewAdapter(mContext) {

    override fun getContentView(infoWindow: InfoWindow): View {
        val windowMapView =
            LayoutInflater.from(mContext).inflate(R.layout.dialog_window_map_inifo, parent as ViewGroup, false)
        val streetImageIV: ImageView =
            windowMapView.findViewById(R.id.iv_street_image)
        windowMapView.tv_content.text = title
        //TODO: 해당 이미지가 정상 경로에 있지만, 가져오지 못 함.
        Glide.with(streetImageIV.context)
            .load(imgUrl)
            .addListener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .into(streetImageIV);

        return windowMapView
    }
}