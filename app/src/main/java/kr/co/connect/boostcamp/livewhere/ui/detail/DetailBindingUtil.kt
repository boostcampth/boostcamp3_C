package kr.co.connect.boostcamp.livewhere.ui.detail

import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.BarChart
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.data.SharedPreferenceStorage
import kr.co.connect.boostcamp.livewhere.model.*
import kr.co.connect.boostcamp.livewhere.ui.detail.adapter.DetailReviewRvAdapter
import kr.co.connect.boostcamp.livewhere.ui.detail.adapter.DetailTransactionRvAdapter
import kr.co.connect.boostcamp.livewhere.ui.map.StreetMapActivity
import kr.co.connect.boostcamp.livewhere.util.*


@BindingAdapter("setProgress")
fun setProgress(view: View, isVisible: Boolean?) {
    val handler = Handler()
    if (isVisible != null) {
        if (!isVisible) view.visibility = View.VISIBLE
        else {
            handler.postDelayed(
                {
                    view.visibility = View.GONE
                }, 1500
            )
//            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("setLoadingLottie")
fun setLoadingLottie(view: LottieAnimationView, isVisible: Boolean?) {
    val handler = Handler()
    if (isVisible != null) {
        if (!isVisible) view.visibility = View.VISIBLE
        else {
            handler.postDelayed(
                {
                    view.visibility = View.GONE
                }, 1500
            )
//            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("setClickable")
fun setClickable(view: View, isClickable: Boolean?) {
    if (isClickable != null) {
        if (isClickable) view.isClickable = isClickable
        else view.isClickable = !isClickable
    }
}

@BindingAdapter("setBarChart")
fun setBarChart(barChart: BarChart, list: LiveData<List<HouseAvgPrice>>) {
    if (!list.value.isNullOrEmpty()) {
        BarChartUtil.showChart(barChart)
        BarChartUtil.setChartData(barChart, list.value!!)
    } else {
        barChart.setNoDataText(EMPTY_BARCHART_TEXT)
        barChart.data = null
    }
    barChart.notifyDataSetChanged()
    barChart.invalidate()
}

@BindingAdapter("setDetailImage") //로드뷰 이미지 세팅
fun setDetailImage(imageView: AppCompatImageView, location: LiveData<String>?) {
    try {
        Glide.with(imageView.context)
//            .load("https://maps.googleapis.com/maps/api/streetview?size=360x200&location=${location!!.value}&key=${BuildConfig.GoogleApiKey}")
            .load(
                imageView.context.getString(
                    R.string.glide_street_img_url,
                    location!!.value,
                    BuildConfig.GoogleApiKey
                )
            )
            .into(imageView)
    } catch (e: KotlinNullPointerException) {
        Toast.makeText(imageView.context, "이미지 없음", Toast.LENGTH_SHORT).show()
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

@BindingAdapter("setRvItems") //과거 거래내역 rv
fun setRvItems(recyclerView: RecyclerView, itemList: List<PastTransaction>?) {
    if (itemList != null) {
        (recyclerView.adapter as DetailTransactionRvAdapter).setData(itemList)
    } else {
        // TODO 데이터 정보 없음 처리.
    }
}

@BindingAdapter("setReviewDelete")
fun setReviewDelete(imageButton: ImageButton,item :Review){
    val pref = SharedPreferenceStorage(imageButton.context)
    if(item.id==pref.uuid){
        imageButton.visibility= View.VISIBLE
    }else{
        imageButton.visibility= View.GONE
    }
}

@BindingAdapter("setReviews")
fun setReviews(recyclerView: RecyclerView, reviewList: List<Review>?) {
    if (!reviewList.isNullOrEmpty()) {
        (recyclerView.adapter as DetailReviewRvAdapter).setData(reviewList)
    }else{
        (recyclerView.adapter as DetailReviewRvAdapter).setData(listOfNotNull())
    }
}

@BindingAdapter("setReviewsEmpty")
fun setReviewsEmpty(textView: TextView, reviewList: List<Review>?) {
    if (!reviewList.isNullOrEmpty()) {
        textView.visibility = View.GONE
    } else {
        textView.visibility = View.VISIBLE
    }
}

@BindingAdapter("setPreReview")
fun setPreReview(textView: TextView, review: List<Review>?) {
    if (!review.isNullOrEmpty()) {
        when (textView.id) {
            R.id.detail_fragment_tv_review_date -> textView.text = review[0].date
            R.id.detail_fragment_tv_review_nickname -> textView.text = review[0].nickname
            R.id.detail_fragment_tv_review_contents -> textView.text = review[0].contents
        }
    } else {
        when (textView.id) {
            R.id.detail_fragment_tv_review_contents -> textView.text = EMPTY_REVIEW_TEXT
        }
    }
}


@BindingAdapter("setVmText")
fun setText(view: TextView, text: CharSequence?) {
//    view.text = text
}

@InverseBindingAdapter(attribute = "setVmText", event = "android:textAttrChanged")
fun getTextString(textView: TextView): String {
    return textView.text.toString()
}

@BindingAdapter("android:textAttrChanged")
fun setTextWatcher(view: TextView, textAttrChanged: InverseBindingListener) {
    view.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textAttrChanged.onChange()
        }

    })
}

@BindingAdapter("setBuildingTitle")
fun setBuildingTitle(view: TextView, name: String?) {
    view.text = name
}

@BindingAdapter("setBookmarkImage")
fun setBookmarkImage(bookmark: ImageButton, boolean: Boolean) = when {
    boolean -> bookmark.setImageResource(R.drawable.ic_bookmark_white_24dp)
    else -> bookmark.setImageResource(R.drawable.ic_bookmark_border_white_24dp)
}


@BindingAdapter("setImageMessage")
fun setImageMessage(view: TextView, count: Int?) {
    if (count!=null) {
        view.text = view.context.getString(R.string.detail_image_message, count)
    }
}

@BindingAdapter("setButtonColor") //시세추이 버튼 색상
fun setButtonColor(view: Button, type: Int) = when (type) {
    TYPE_CHARTER -> if (view.id == R.id.detail_fragment_btn_trend_price_charter) {
        view.setBackgroundResource(R.drawable.background_detail_trend_price_button_active)
        view.setTextColor(Color.parseColor(WHITE_COLOR))
    } else {
        view.setBackgroundResource(R.drawable.background_detail_trend_price_button_inactive)
        view.setTextColor(Color.parseColor(PRIMARY_COLOR))
    }
    TYPE_MONTHLY -> if (view.id == R.id.detail_fragment_btn_trend_price_monthly) {
        view.setBackgroundResource(R.drawable.background_detail_trend_price_button_active)
        view.setTextColor(Color.parseColor(WHITE_COLOR))
    } else {
        view.setBackgroundResource(R.drawable.background_detail_trend_price_button_inactive)
        view.setTextColor(Color.parseColor(PRIMARY_COLOR))
    }
    else -> {
    }
}

@BindingAdapter("setSortColor")
fun setSortColor(view: View, sortType: Int) = when (sortType) {
    SORT_BY_AREA, SORT_BY_AREA_REV -> if (view.id == R.id.past_transaction_header_area) {
        view.setBackgroundColor(Color.parseColor(PRIMARY_DARK_COLOR))
    } else {
        view.setBackgroundColor(Color.parseColor(PRIMARY_COLOR))
    }
    SORT_BY_TYPE, SORT_BY_TYPE_REV -> if (view.id == R.id.past_transaction_header_type) {
        view.setBackgroundColor(Color.parseColor(PRIMARY_DARK_COLOR))
    } else {
        view.setBackgroundColor(Color.parseColor(PRIMARY_COLOR))
    }
    SORT_BY_YEAR, SORT_BY_YEAR_REV -> if (view.id == R.id.past_transaction_header_contract_year) {
        view.setBackgroundColor(Color.parseColor(PRIMARY_DARK_COLOR))
    } else {
        view.setBackgroundColor(Color.parseColor(PRIMARY_COLOR))
    }
    else -> {
        view.setBackgroundColor(Color.parseColor(PRIMARY_COLOR))
    }
}