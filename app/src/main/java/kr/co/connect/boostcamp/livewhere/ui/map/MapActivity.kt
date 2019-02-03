package kr.co.connect.boostcamp.livewhere.ui.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kr.co.connect.boostcamp.livewhere.BR
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityMapBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapActivity : AppCompatActivity() {
    private val mapViewModel: MapViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val activityMapBinding: ActivityMapBinding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        activityMapBinding.setLifecycleOwner(this)
        activityMapBinding.setVariable(BR.mapViewModel, mapViewModel)
        activityMapBinding.setVariable(BR.mlFloatBtn, activityMapBinding.mlFloatBtn)
        activityMapBinding.setVariable(BR.mlBackdrop, activityMapBinding.mlBackdrop)
    }
}