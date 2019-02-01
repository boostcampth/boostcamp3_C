package kr.co.connect.boostcamp.livewhere.di

import kr.co.connect.boostcamp.livewhere.repository.MapRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.map.MapViewModel
import kr.co.connect.boostcamp.livewhere.util.MapUtilImpl
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val mapModule = module {
    single("mapUtil") { MapUtilImpl() }
    single("mapRepository"){MapRepositoryImpl(get("api"))}
    viewModel { MapViewModel(get("mapUtil"),get("mapRepository")) }
}