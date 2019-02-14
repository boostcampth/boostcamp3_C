package kr.co.connect.boostcamp.livewhere.util

import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter


class XAxisValueFormatter(private val mValues: Array<String?>) : IAxisValueFormatter {

    val decimalDigits: Int
        get() = 0

    override fun getFormattedValue(value: Float, axis: AxisBase): String? {
        return if(value<mValues.size)
            mValues[value.toInt()]
        else " "
    }
}