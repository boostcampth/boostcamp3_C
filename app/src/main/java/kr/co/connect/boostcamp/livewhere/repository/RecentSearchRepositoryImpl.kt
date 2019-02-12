package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Observable
import kr.co.connect.boostcamp.livewhere.data.dao.RecentSearchDAO
import kr.co.connect.boostcamp.livewhere.data.database.AppDataBase
import kr.co.connect.boostcamp.livewhere.data.entity.RecentSearchEntity

class RecentSearchRepositoryImpl(private val appDataBase: AppDataBase) : RecentSearchRepository {
    override fun getRecentSearch(): Observable<List<RecentSearchEntity>> {
        return appDataBase.recentSearchDao().getAll().filter { it.isNotEmpty() }
            .toObservable()
    }

    override fun setRecentSearch(recentSearch: RecentSearchEntity): Observable<Boolean> {
        return Observable.fromCallable {
            appDataBase.recentSearchDao().insertRecentSearch(recentSearch)
            return@fromCallable true
        }
    }
}