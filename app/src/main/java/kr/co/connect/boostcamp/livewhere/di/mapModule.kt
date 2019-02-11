package kr.co.connect.boostcamp.livewhere.di

import kr.co.connect.boostcamp.livewhere.repository.MapRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.map.MapActivityManagerImpl
import kr.co.connect.boostcamp.livewhere.ui.map.MapViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val mapModule = module {
    single("mapManager") { MapActivityManagerImpl() }
    single("mapRepository"){MapRepositoryImpl(get("api"),get("reverseApi"), get())}
    viewModel { MapViewModel(get("mapManager"),get("mapRepository")) }
}