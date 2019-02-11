package kr.co.connect.boostcamp.livewhere.ui.map

import android.content.Context
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.ui.map.interfaces.MapActivityManager

class MapActivityManagerImpl : MapActivityManager {
    private val behaviorSubject = BehaviorSubject.createDefault(0L)
    fun getBackPressedDisposable() = behaviorSubject.buffer(2, 1)
        .map { buffers ->
            return@map buffers[1] - buffers[0] < 1000
        }.observeOn(AndroidSchedulers.mainThread())!!

    override fun backEvent() {
        behaviorSubject.onNext(System.currentTimeMillis())
    }

    override fun showToast(context: Context) {
        Toast.makeText(context, context.getString(R.string.message_toast_back_btn), Toast.LENGTH_SHORT).show()
    }

}