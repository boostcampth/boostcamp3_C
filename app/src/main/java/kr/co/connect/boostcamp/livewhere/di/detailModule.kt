package kr.co.connect.boostcamp.livewhere.di

import kr.co.connect.boostcamp.livewhere.repository.DetailRepository
import kr.co.connect.boostcamp.livewhere.repository.DetailRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val detailModule = module {

    factory("detailRepository") { DetailRepositoryImpl(get("api")) as DetailRepository}

    viewModel { DetailViewModel(get("detailRepository")) }

}