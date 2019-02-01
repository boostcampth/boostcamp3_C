package kr.co.connect.boostcamp.livewhere.util

import android.content.Context
import android.graphics.Bitmap
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPoint

interface MapUtil {
    fun filterMap(category: CharCategory)

    fun findMyLocation()

    fun makeMarker(point: TMapPoint, markerBitmap: Bitmap): TMapMarkerItem

    fun makeOverlay(context: Context, tMapPoint: TMapPoint, title: String, subTitle: String) : MarkerOverlay
}