package kr.co.connect.boostcamp.livewhere.util

import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kr.co.connect.boostcamp.livewhere.model.HouseAvgPrice


object BarChartUtil {
    fun showChart(barChart: BarChart) {
        barChart.setFitBars(true)
        barChart.animateXY(1000, 1000)
        barChart.invalidate()
    }

    fun setChartData(barChart: BarChart, list: List<HouseAvgPrice>) { // list -> 계약 년도, 해당 년도 평균 거래가
        val values = arrayOfNulls<String>(list.size)
        val entryList = ArrayList<BarEntry>()
        list.forEachIndexed { i, it ->
            val entry = BarEntry(i.toFloat(), it.avgPrice)
            values[i] = it.year.toInt().toString()
            entryList.add(entry)
        }
        val barDataSet = BarDataSet(entryList, AVG_PRICE)
        barDataSet.color = Color.parseColor(PRIMARY_COLOR_OPACITY)
        val barData = BarData(barDataSet)

        barChart.apply {
            data = barData
            description = null
        }

        barChart.xAxis.apply {
            setLabelCount(list.size, false)
            position = XAxisPosition.BOTTOM
            setDrawAxisLine(true)
            valueFormatter = XAxisValueFormatter(values)
            labelRotationAngle = 45f
            textSize = 9f
            setDrawGridLines(false)
        }
    }
}