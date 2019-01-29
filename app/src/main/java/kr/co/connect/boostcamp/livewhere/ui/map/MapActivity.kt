package kr.co.connect.boostcamp.livewhere.ui.map

import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_map.*
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityMapBinding
import kr.co.connect.boostcamp.livewhere.util.MapHelper
import kr.co.connect.boostcamp.livewhere.util.MapHelperImpl
import kr.co.connect.boostcamp.livewhere.util.MapUtilImpl
import java.util.ArrayList
import javax.inject.Inject

class MapActivity : AppCompatActivity() {
    lateinit var mapUtilImpl: MapUtilImpl
    lateinit var mapHelper: MapHelperImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        /*val tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("aefcae11-4e30-4cc2-b2c8-790a9cde8875")
        ll_map_view.addView(tMapView)
        tMapView.setOnClickListenerCallBack(object : TMapView.OnClickListenerCallback {
            override fun onPressEvent(
                p0: ArrayList<TMapMarkerItem>?,
                p1: ArrayList<TMapPOIItem>?,
                p2: TMapPoint?,
                p3: PointF?
            ): Boolean {
                val tampItem = TMapMarkerItem()
                tampItem.setPosition(p2!!.latitude.toFloat(), p2.longitude.toFloat())
                Log.d("pos",tampItem.positionX.toString()+", "+tampItem.positionY)
                return true
            }

            override fun onPressUpEvent(
                p0: ArrayList<TMapMarkerItem>?,
                p1: ArrayList<TMapPOIItem>?,
                p2: TMapPoint?,
                p3: PointF?
            ): Boolean {
                return true
            }
        })*/
    }
}