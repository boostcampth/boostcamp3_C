package kr.co.connect.boostcamp.livewhere.ui.detail

import androidx.databinding.BindingAdapter
import com.github.mikephil.charting.charts.BarChart
import kr.co.connect.boostcamp.livewhere.model.HouseAvgPrice
import kr.co.connect.boostcamp.livewhere.util.BarChartUtil

@BindingAdapter("setBarChart")
fun setBarChart(barChart: BarChart,list: ArrayList<HouseAvgPrice>){
    BarChartUtil.setChartData(barChart,list)
    BarChartUtil.showChart(barChart)
}
