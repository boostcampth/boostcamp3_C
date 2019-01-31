package kr.co.connect.boostcamp.livewhere.ui.map

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapMarkerItem
import kr.co.connect.boostcamp.livewhere.BR
import kr.co.connect.boostcamp.livewhere.databinding.ActivityMapBinding
import org.koin.androidx.viewmodel.ext.android.viewModel



class MapActivity : AppCompatActivity() {
    private val mapViewModel: MapViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMapBinding: ActivityMapBinding = DataBindingUtil.setContentView(this, kr.co.connect.boostcamp.livewhere.R.layout.activity_map)
        activityMapBinding.setVariable(BR.mapViewModel, mapViewModel)
        activityMapBinding.setVariable(BR.mlFloatBtn, activityMapBinding.mlFloatBtn)
        activityMapBinding.setVariable(BR.mlBackdrop, activityMapBinding.mlBackdrop)
        activityMapBinding.mapViewModel!!.mapHelperImpl.tMapView.setOnLongClickListenerCallback { markerList, polist, point ->
            val tMapMarkerItem : TMapMarkerItem = TMapMarkerItem()
            val markerBitmap = BitmapFactory.decodeResource(resources, kr.co.connect.boostcamp.livewhere.R.drawable.ic_marker_24dp)
            tMapMarkerItem.tMapPoint = point
            tMapMarkerItem.icon = markerBitmap
            tMapMarkerItem.setPosition(0.5f,1f)
            tMapMarkerItem.name = "testMarker"
            activityMapBinding.mapViewModel!!.mapHelperImpl.tMapView.addMarkerItem("marker", tMapMarkerItem)
            try {
                val address = TMapData().convertGpsToAddress(point.getLatitude(), point.getLongitude())
                val handler = Handler()
                handler.post {  Toast.makeText(this, address, Toast.LENGTH_SHORT).show() }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }
}