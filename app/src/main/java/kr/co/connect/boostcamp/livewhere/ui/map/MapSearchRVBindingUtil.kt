package kr.co.connect.boostcamp.livewhere.ui.map

import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.BindingAdapter
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.model.MarkerInfo
import kr.co.connect.boostcamp.livewhere.ui.detail.DetailActivity

@BindingAdapter(value = ["onWebClickListener"])
fun LinearLayout.setOnWebClickListener(placeUrl: String) {
    setOnClickListener {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(it.context, Uri.parse(placeUrl))
    }
}

@BindingAdapter(value = ["onCallClickListener"])
fun LinearLayout.setOnCallListener(phone: String) {
    val dial = "tel:$phone"
    setOnClickListener {
        context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(dial)))
    }
}

@BindingAdapter(value =["onText"])
fun TextView.onTextView(content:String){
    text = content
}

@BindingAdapter(value=["onIntent"])
fun LinearLayout.startActivityWithIntent(markerInfo:MarkerInfo){
    setOnClickListener {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("markerInfo", markerInfo)
        context.startActivity(intent)
    }
}

@BindingAdapter(value=["onDrawCategory"])
fun ImageView.setOnDrawCategory(category:String){
    when(category){
        "학교","어린이집,유치원"-> setBackgroundResource(R.drawable.ic_school)
        "편의점","대형마트"-> setBackgroundResource(R.drawable.ic_mall)
        "음식점"->setBackgroundResource(R.drawable.ic_signboard)
        "카페"->setBackgroundResource(R.drawable.ic_cafe)
        "병원"->setBackgroundResource(R.drawable.ic_hospital)
    }
}