package kr.co.connect.boostcamp.livewhere.ui.map

import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.connect.boostcamp.livewhere.util.MapHelperImpl
import kr.co.connect.boostcamp.livewhere.util.MapUtilImpl

class MapViewModel(val mapHelperImpl: MapHelperImpl, val mapUtilImpl: MapUtilImpl) : ViewModel(),MapListener{

    private val _currentMLid = MutableLiveData<MotionLayout>()
    val currentml : LiveData<MotionLayout>
        get() = _currentMLid


    override fun onTransitionCompleted(ml: MotionLayout?, currentId: Int) {
        if (ml != null) {
            _currentMLid.postValue(ml)
        }
    }


}