package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Observable
import kr.co.connect.boostcamp.livewhere.data.dao.RecentSearchDAO
import kr.co.connect.boostcamp.livewhere.data.entity.RecentSearchEntity

class RecentSearchRepositoryImpl(private val recentSearchDao: RecentSearchDAO) : RecentSearchRepository {
    override fun getRecentSearch(): Observable<List<RecentSearchEntity>> {
        return Observable.fromCallable {
            return@fromCallable recentSearchDao.getAll()
        }
    }

    override fun setRecentSearch(recentSearch: RecentSearchEntity): Observable<Boolean> {
        return Observable.fromCallable {
            recentSearchDao.insertRecentSearch(recentSearch)
            return@fromCallable true
        }
    }
}