package kr.co.connect.boostcamp.livewhere.ui.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.util.FusedLocationSource
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityMapBinding
import kr.co.connect.boostcamp.livewhere.ui.map.adapter.MapSearchRVAdapter
import kr.co.connect.boostcamp.livewhere.util.LAT
import kr.co.connect.boostcamp.livewhere.util.LON
import kr.co.connect.boostcamp.livewhere.util.MapUtilImpl.Companion.LOCATION_PERMISSION_REQUEST_CODE
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapActivity : AppCompatActivity() {
    private val mapViewModel: MapViewModel by viewModel()
    private lateinit var activityMapBinding: ActivityMapBinding
    private lateinit var locationSource: FusedLocationSource


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        activityMapBinding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        activityMapBinding.lifecycleOwner = this
        activityMapBinding.mapViewModel = mapViewModel
        activityMapBinding.locationSource = locationSource
        activityMapBinding.setMlFloatBtn(activityMapBinding.mlFloatBtn)
        activityMapBinding.setMlBackdrop(activityMapBinding.mlBackdrop)
        activityMapBinding.mvMainNaver.onCreate(savedInstanceState)
        activityMapBinding.mvMainNaver.getMapAsync(mapViewModel)


        activityMapBinding.rvSearchMap.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = MapSearchRVAdapter(mapViewModel)
        }

        val lat = intent.getStringExtra(LAT)
        val lng = intent.getStringExtra(LON)
        if(lat!=null){
            val latLng = LatLng(lat.toDouble(),lng.toDouble())
            intent.extras.clear()
            mapViewModel.loadHousePrice(latLng)
        }else{
            mapViewModel.onInitActivityStatus()
        }
    }

    override fun onStart() {
        super.onStart()
        activityMapBinding.mvMainNaver.onStart()//naverMap객체는 라이프사이클에 종속되어야 합니다.

    }

    override fun onResume() {
        super.onResume()
        activityMapBinding.mvMainNaver.onResume()
    }

    override fun onPause() {
        super.onPause()
        activityMapBinding.mvMainNaver.onPause()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        activityMapBinding.mvMainNaver.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        activityMapBinding.mvMainNaver.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        activityMapBinding.mvMainNaver.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        activityMapBinding.mvMainNaver.onDestroy()
        mapViewModel.stopObservable()
    }

    //자신의 위치 관련한 permission 함수
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}