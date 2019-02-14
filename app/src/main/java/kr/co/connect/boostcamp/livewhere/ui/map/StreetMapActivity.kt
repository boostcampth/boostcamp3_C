package kr.co.connect.boostcamp.livewhere.ui.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityStreetMapBinding


class StreetMapActivity : AppCompatActivity() {
    private lateinit var dataBindingUtil: ActivityStreetMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBindingUtil = DataBindingUtil.setContentView(this, R.layout.activity_street_map)

        val lat = intent.getStringExtra("lat")
        val lng = intent.getStringExtra("lng")

        val latLng = LatLng(lat.toDouble(), lng.toDouble())

        val fragmentStreetView: SupportStreetViewPanoramaFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_street_view) as SupportStreetViewPanoramaFragment

        fragmentStreetView.getStreetViewPanoramaAsync { streetViewPanorama ->
            streetViewPanorama.setPosition(latLng)
        }
    }
}