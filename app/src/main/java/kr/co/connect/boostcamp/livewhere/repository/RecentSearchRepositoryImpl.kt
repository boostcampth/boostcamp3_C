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

    override fun setRecentSearch(recentSearch: RecentSearchEntity): Boolean {
        //TODO: Thread Handle
        val runnable = Runnable {
            recentSearchDAO.insertRecentSearch(recentSearch)
        }
        val thread = Thread(runnable)
        thread.start()
        return true
    }

    override fun deleteRecentSearch(): Boolean {
        //TODO: Thread Handle
        val runnable = Runnable {
            recentSearchDAO.deleteAll()
        }
        val thread = Thread(runnable)
        thread.start()
        return true
    }
}