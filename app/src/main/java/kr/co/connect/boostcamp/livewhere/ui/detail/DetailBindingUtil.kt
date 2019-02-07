package kr.co.connect.boostcamp.livewhere.ui.detail

import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.BarChart
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.model.HouseAvgPrice
import kr.co.connect.boostcamp.livewhere.model.PastTransaction
import kr.co.connect.boostcamp.livewhere.model.RecentPrice
import kr.co.connect.boostcamp.livewhere.model.Review
import kr.co.connect.boostcamp.livewhere.ui.detail.adapter.DetailReviewRvAdapter
import kr.co.connect.boostcamp.livewhere.ui.detail.adapter.DetailTransactionRvAdapter
import kr.co.connect.boostcamp.livewhere.util.BarChartUtil
import kr.co.connect.boostcamp.livewhere.util.No_Review_Text
import kr.co.connect.boostcamp.livewhere.util.No_barChart_text


@BindingAdapter("setBarChart")
fun setBarChart(barChart: BarChart, list: LiveData<ArrayList<HouseAvgPrice>>) {
    try{
        BarChartUtil.setChartData(barChart, list.value!!)
        BarChartUtil.showChart(barChart)
        barChart.notifyDataSetChanged()
    } catch (e:KotlinNullPointerException) {
        barChart.setNoDataText(No_barChart_text)
    }
}

@BindingAdapter("setDetailImage") //로드뷰 이미지 세팅
fun setDetailImage(imageView: AppCompatImageView, location: LiveData<String>?) {
    try {
        Glide.with(imageView.context)
            .load("https://maps.googleapis.com/maps/api/streetview?size=360x200&location=${location!!.value}&key=${BuildConfig.GoogleApiKey}")
            .into(imageView)
    }catch (e:KotlinNullPointerException){
        Toast.makeText(imageView.context,"이미지 없음",Toast.LENGTH_SHORT).show()
    }
}

@BindingAdapter("setRecentPrice")
fun setRecentPrice(textView: TextView, recentPrice: LiveData<RecentPrice>) {
    try {
        when (textView.id) {
            R.id.detail_fragment_tv_charter -> {
                textView.text =
                    textView.context.getString(R.string.recent_charter_price, recentPrice.value!!.charterPrice)
            }
            R.id.detail_fragment_tv_monthly_rent -> {
                textView.text =
                    textView.context.getString(R.string.recent_monthly_price, recentPrice.value!!.monthlyPrice)
            }
        }
    } catch (e: KotlinNullPointerException) {
        when (textView.id) {
            R.id.detail_fragment_tv_charter -> {
                textView.text = "전세 정보 없음"
            }
            R.id.detail_fragment_tv_monthly_rent -> {
                textView.text = "월세 정보 없음"
            }
        }
    }
}

@BindingAdapter("setRvItems")
fun setRvItems(recyclerView: RecyclerView, itemList: List<PastTransaction>?) {
    if (itemList != null) {
        (recyclerView.adapter as DetailTransactionRvAdapter).setData(itemList)
    } else {
        // TODO 데이터 정보 없음 처리.
    }
}

@BindingAdapter("setReviews")
fun setReviews(recyclerView: RecyclerView, reviewList: List<Review>?) {
    if (reviewList != null) {
        (recyclerView.adapter as DetailReviewRvAdapter).setData(reviewList)
    } else {
        // TODO 데이터 정보 없음 처리.
    }
}

@BindingAdapter("setPreReview")
fun setPreReview(textView: TextView, review: LiveData<ArrayList<Review>>) {
    try {
        when (textView.id) {
            R.id.detail_fragment_tv_review_id -> textView.text = review.value!![0].id
            R.id.detail_fragment_tv_review_nickname -> textView.text = review.value!![0].nickname
            R.id.detail_fragment_tv_review_contents -> textView.text = review.value!![0].contents
        }
    } catch (e: KotlinNullPointerException) {
        when (textView.id) {
            R.id.detail_fragment_tv_review_contents -> textView.text = No_Review_Text
        }
    }


}