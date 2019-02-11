package kr.co.connect.boostcamp.livewhere.util

import com.github.mikephil.charting.charts.BarChart
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

    fun setChartData(barChart: BarChart, list: ArrayList<HouseAvgPrice>) { // list -> 계약 년도, 해당 년도 평균 거래가
        val entryList = ArrayList<BarEntry>()
        list.forEach {
            val entry = BarEntry(it.year, it.avgPrice)
            entryList.add(entry)
        }
        val barDataSet = BarDataSet(entryList, AVG_PRICE)
        val barData = BarData(barDataSet)

        barChart.data = barData
    }
}