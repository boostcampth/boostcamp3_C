package kr.co.connect.boostcamp.livewhere.ui

import android.view.View
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference
import java.util.*

open class BaseViewModel : ViewModel(){

    private val compositeDisposable = CompositeDisposable()
    private var mNavigator: WeakReference

    fun addDisposable(d: Disposable) {
        compositeDisposable.add(d)
    }

    fun getCompositeDisposable() = compositeDisposable

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun setNavigator(navigator: View) {
        this.mNavigator = navigator
    }

    fun getNavigator(): View {
        return mNavigator.get()
    }
}