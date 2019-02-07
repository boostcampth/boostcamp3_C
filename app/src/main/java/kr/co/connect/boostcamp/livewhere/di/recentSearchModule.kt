package kr.co.connect.boostcamp.livewhere.di

import kr.co.connect.boostcamp.livewhere.ui.main.HomeActivityViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val recentSearchModule = module {
    viewModel { HomeActivityViewModel() }
}