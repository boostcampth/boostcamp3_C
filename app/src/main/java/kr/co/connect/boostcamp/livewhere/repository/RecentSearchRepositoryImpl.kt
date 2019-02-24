package kr.co.connect.boostcamp.livewhere.repository

import io.reactivex.Observable
import kr.co.connect.boostcamp.livewhere.data.dao.RecentSearchDAO
import kr.co.connect.boostcamp.livewhere.data.entity.RecentSearchEntity
import java.util.concurrent.Callable

class RecentSearchRepositoryImpl(private val recentSearchDAO: RecentSearchDAO) : RecentSearchRepository {
    override fun getRecentSearch(): Observable<List<RecentSearchEntity>> {
        return Observable.fromCallable(object : Callable<List<RecentSearchEntity>> {
            override fun call(): List<RecentSearchEntity> {
                return recentSearchDAO.getAll()
            }
        })
    }

    override fun setRecentSearch(recentSearch: RecentSearchEntity): Observable<Boolean> {
        return Observable.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {
                recentSearchDAO.insertRecentSearch(recentSearch)
                return true
            }
        })
    }

    override fun deleteRecentSearch(): Observable<Boolean> {
        return Observable.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {
                recentSearchDAO.deleteAll()
                return true
            }
        })
    }
}