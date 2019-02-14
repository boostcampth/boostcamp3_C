package kr.co.connect.boostcamp.livewhere.util

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
        val barData = BarData(barDataSet)
        barChart.data = barData

        barChart.xAxis.setLabelCount(list.size, false)
        barChart.xAxis.position = XAxisPosition.BOTTOM
        barChart.xAxis.setDrawAxisLine(true)
        barChart.xAxis.valueFormatter = XAxisValueFormatter(values)
        barChart.xAxis.labelRotationAngle = 45f
        barChart.xAxis.textSize = 9f

    }
}