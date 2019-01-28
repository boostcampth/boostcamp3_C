package kr.co.connect.boostcamp.livewhere.ui.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityMapBinding
import kr.co.connect.boostcamp.livewhere.util.MapUtilIImp
import javax.inject.Inject

class MapActivity : AppCompatActivity(){
    @Inject
    lateinit var mapUtilImp:MapUtilIImp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapBindingUtil:ActivityMapBinding=DataBindingUtil.setContentView(this, R.layout.activity_map)
    }
}