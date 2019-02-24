package kr.co.connect.boostcamp.livewhere.di

import kr.co.connect.boostcamp.livewhere.repository.*
import kr.co.connect.boostcamp.livewhere.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val detailModule = module {

    factory("reviewRepository"){ ReviewRepository() }
    factory("bookmarkRemoteRepository") {BookmarkUserRepository()}
    factory("bookmarkLocalRepository") {BookmarkRepositoryImpl(get("bookmarkDAO"))}

    viewModel { DetailViewModel(get("reviewRepository"),get("bookmarkRemoteRepository"),get("bookmarkLocalRepository"),get()) }
}


