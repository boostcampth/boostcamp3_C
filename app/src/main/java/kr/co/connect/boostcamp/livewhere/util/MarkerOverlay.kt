package kr.co.connect.boostcamp.livewhere.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.util.DisplayMetrics
import android.view.View.MeasureSpec
import android.view.WindowManager
import com.skt.Tmap.TMapMarkerItem2
import com.skt.Tmap.TMapView


class MarkerOverlay(private val context: Context, labelName: String, id: String) : TMapMarkerItem2() {

    var dm: DisplayMetrics? = null
    var balloonView: BalloonOverlayView? = null

    init {
        dm = DisplayMetrics()
        val wmgr = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wmgr.defaultDisplay.getMetrics(dm)

        balloonView = BalloonOverlayView(context, labelName, id)

        balloonView!!.measure(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )

        balloonView!!.layout(0, 0, balloonView!!.measuredWidth, balloonView!!.measuredHeight)
    }

    override fun draw(canvas: Canvas, mapView: TMapView, showCallout: Boolean) {
        val x = mapView.getRotatedMapXForPoint(tMapPoint.latitude, tMapPoint.longitude)
        val y = mapView.getRotatedMapYForPoint(tMapPoint.latitude, tMapPoint.longitude)

        canvas.save()
        canvas.rotate(-mapView.rotate, mapView.centerPointX.toFloat(), mapView.centerPointY.toFloat())

        balloonView!!.measure(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )

        val nTempX = x - balloonView!!.measuredWidth / 2
        val nTempY = y - balloonView!!.measuredHeight

        canvas.translate(nTempX.toFloat(), nTempY.toFloat())
        balloonView!!.draw(canvas)
        canvas.restore()
    }

    override fun onSingleTapUp(point: PointF?, mapView: TMapView?): Boolean {
        mapView!!.showCallOutViewWithMarkerItemID(id)
        return false
    }
}