package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Observable
import kr.co.connect.boostcamp.livewhere.data.entity.RecentSearchEntity

interface RecentSearchRepository {
    fun getRecentSearch(): Observable<List<RecentSearchEntity>>
    fun setRecentSearch(recentSearch: RecentSearchEntity): Boolean
    fun deleteRecentSearch(): Boolean
}