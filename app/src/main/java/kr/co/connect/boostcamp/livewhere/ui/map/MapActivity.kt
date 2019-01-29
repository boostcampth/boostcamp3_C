package kr.co.connect.boostcamp.livewhere.ui.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.util.MapHelperImpl
import kr.co.connect.boostcamp.livewhere.util.MapUtilImpl

class MapActivity : AppCompatActivity() {
    lateinit var mapUtilImpl: MapUtilImpl
    lateinit var mapHelper: MapHelperImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
    }
}