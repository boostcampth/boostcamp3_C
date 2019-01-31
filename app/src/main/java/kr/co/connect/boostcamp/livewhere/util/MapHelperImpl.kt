package kr.co.connect.boostcamp.livewhere.util

import androidx.lifecycle.ViewModel
import com.skt.Tmap.TMapView

class MapHelperImpl(val tMapView: TMapView) : MapHelper, ViewModel(){
    override fun getTMapView() {

    }

    override fun close() {

    }

    override fun create(): TMapView = tMapView
}