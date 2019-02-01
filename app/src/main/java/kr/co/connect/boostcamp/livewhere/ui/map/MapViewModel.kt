package kr.co.connect.boostcamp.livewhere.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skt.Tmap.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.co.connect.boostcamp.livewhere.repository.MapRepositoryImpl
import kr.co.connect.boostcamp.livewhere.util.HttpResult
import kr.co.connect.boostcamp.livewhere.util.MapUtilImpl
import java.util.*

class MapViewModel(val mapUtilImpl: MapUtilImpl, val mapRepository: MapRepositoryImpl) : ViewModel(),
    TMapView.OnLongClickListenerCallback {
    private val _pointLiveData: MutableLiveData<TMapPoint> = MutableLiveData()
    val pointLiveData: LiveData<TMapPoint>
        get() = _pointLiveData

    override fun onLongPressEvent(
        markerList: ArrayList<TMapMarkerItem>?,
        polist: ArrayList<TMapPOIItem>?,
        point: TMapPoint?
    ) {
        _pointLiveData.postValue(point)
        Single.fromCallable { TMapData().convertGpsToAddress(point!!.latitude, point.longitude) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ address ->
                mapRepository.getHouseDetail(address).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({ body ->
                        var title = ""
                        var subTitle = ""
                        if (body.houseStatusCode == HttpResult.RESULT_200.response) {
                            title = body.houseList[0].deposite + "/" + body.houseList[0].fee
                            subTitle = "최근 거래:" + body.houseList[0].contractYear//변경 예정인 테스트 코드
                        } else if (body.houseStatusCode == HttpResult.RESULT_204.response) {
                            title = "최근 거래내역이 없습니다."//변경 예정인 테스트 코드
                            subTitle = ""
                        }
                    }, {})
            }, {})
    }
}