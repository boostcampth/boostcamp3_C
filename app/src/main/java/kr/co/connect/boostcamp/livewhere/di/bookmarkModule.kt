package kr.co.connect.boostcamp.livewhere.di;

import kr.co.connect.boostcamp.livewhere.repository.BookmarkRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.main.BookmarkViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val bookmarkModule = module {
    single("bookmarkRepository") { BookmarkRepositoryImpl(get("bookmarkDatabaseModule")) }

    viewModel { BookmarkViewModel(get("bookmarkRepository")) }
}