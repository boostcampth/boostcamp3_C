package kr.co.connect.boostcamp.livewhere.util

import android.content.Context
import android.graphics.Bitmap
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint

class MapUtilImpl : MapUtil {
    override fun makeOverlay(context: Context, tMapPoint: TMapPoint, title: String, subTitle: String): MarkerOverlay {
        val strID = "TMapMarkerItem2"
        val markerOverlay = MarkerOverlay(context, "custom", "marker")
        markerOverlay.id = strID
        markerOverlay.tMapPoint = TMapPoint(tMapPoint.latitude, tMapPoint.longitude)
        markerOverlay.balloonView?.setTitle(title)
        markerOverlay.balloonView?.setSubTitle(subTitle)

        return markerOverlay
    }

    override fun makeMarker(point: TMapPoint, markerBitmap: Bitmap): TMapMarkerItem {
        val tMapMarkerItem: TMapMarkerItem = TMapMarkerItem()
        tMapMarkerItem.tMapPoint = point
        tMapMarkerItem.icon = markerBitmap
        tMapMarkerItem.setPosition(0.5f, 1f)
        tMapMarkerItem.name = "testMarker"
        return tMapMarkerItem
    }


    override fun findMyLocation() {

    }

    override fun filterMap(category: CharCategory) {

    }
}