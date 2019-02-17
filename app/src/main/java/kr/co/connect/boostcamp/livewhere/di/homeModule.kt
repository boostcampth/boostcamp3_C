package kr.co.connect.boostcamp.livewhere.di

import kr.co.connect.boostcamp.livewhere.repository.BookmarkRepositoryImpl
import kr.co.connect.boostcamp.livewhere.repository.RecentSearchRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.main.HomeViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val homeModule = module {
    factory("bookmarkRepository") { BookmarkRepositoryImpl(get("bookmarkDAO")) }
    factory("recentSearchRepository") { RecentSearchRepositoryImpl(get("recentSearchDAO")) }

    viewModel { HomeViewModel(get("bookmarkRepository"), get("recentSearchRepository"), get("kakaoPlace")) }
}