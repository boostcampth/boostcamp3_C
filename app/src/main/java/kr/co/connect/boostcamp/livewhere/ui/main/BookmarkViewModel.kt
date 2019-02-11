package kr.co.connect.boostcamp.livewhere.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.co.connect.boostcamp.livewhere.data.dao.BookmarkDAO
import kr.co.connect.boostcamp.livewhere.data.entity.BookmarkEntity
import kr.co.connect.boostcamp.livewhere.repository.RecentSearchRepositoryImpl
import kr.co.connect.boostcamp.livewhere.ui.BaseViewModel

class BookmarkViewModel(val searchRepositoryImpl: RecentSearchRepositoryImpl) : BaseViewModel() {
    private val _bookmarkEntity = MutableLiveData<List<BookmarkEntity>>()
    val bookmarkEntity: LiveData<List<BookmarkEntity>>
        get() = _bookmarkEntity

    init {}
}