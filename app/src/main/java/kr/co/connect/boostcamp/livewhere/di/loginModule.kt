package kr.co.connect.boostcamp.livewhere.di

import kr.co.connect.boostcamp.livewhere.repository.LoginRepository
import kr.co.connect.boostcamp.livewhere.repository.LoginRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val loginModule = module {
    factory {
        LoginRepositoryImpl() as LoginRepository
    }

    viewModel { LoginViewModel(get()) }
}