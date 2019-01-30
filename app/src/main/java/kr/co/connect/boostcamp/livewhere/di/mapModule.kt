package kr.co.connect.boostcamp.livewhere.di

import com.skt.Tmap.TMapView
import kr.co.connect.boostcamp.livewhere.ui.map.MapViewModel
import kr.co.connect.boostcamp.livewhere.util.MapHelperImpl
import kr.co.connect.boostcamp.livewhere.util.MapUtilImpl
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val mapModule = module {
    single("tMapView"){ TMapView(get()) }
    single("mapHelper") { MapHelperImpl(get()) }
    single("mapUtil") { MapUtilImpl() }
    viewModel { MapViewModel(get("mapHelper"), get("mapUtil")) }
}