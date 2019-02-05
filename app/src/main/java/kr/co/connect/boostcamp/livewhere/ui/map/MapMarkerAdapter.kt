package kr.co.connect.boostcamp.livewhere.ui.map

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.naver.maps.map.overlay.InfoWindow
import kotlinx.android.synthetic.main.dialog_window_map_inifo.view.*
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.ui.detail.DetailActivity


//TODO: info는 그래픽으로 사용되기 때문에, 추후에 해당 어댑터를 삭제하고 recyclerView를 하단에 둘 것입니다.
class MapMarkerAdapter(
    private val mContext: Context,
    private val parent: ViewParent,
    private val imgUrl: String,
    private val linkUrl: String,
    private val title: String,
    private val buttonText: String
) :
    InfoWindow.ViewAdapter() {
    override fun getView(infoWindow: InfoWindow): View {
        val windowMapView =
            LayoutInflater.from(mContext).inflate(R.layout.dialog_window_map_inifo, parent as ViewGroup, false)
        val streetImageIV: ImageView =
            windowMapView.findViewById(R.id.iv_street_image)
        val detailInfoBtn: Button = windowMapView.findViewById(R.id.btn_detail_info)
        detailInfoBtn.apply {
            text = buttonText
            setOnClickListener {
                if (detailInfoBtn.text == mContext.getString(R.string.all_find_house_text)) {
                    val intent = Intent(mContext, DetailActivity::class.java)
                    mContext.startActivity(intent)
                } else if (detailInfoBtn.text == mContext.getString(R.string.all_find_store_text)) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
                    mContext.startActivity(intent)
                }
            }
        }
        windowMapView.tv_content.text = title


        //TODO: 해당 이미지가 정상 경로에 있지만, 가져오지 못 함.
        Glide.with(streetImageIV.context)
            .load(imgUrl)
            .addListener(object : RequestListener<Drawable> {
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