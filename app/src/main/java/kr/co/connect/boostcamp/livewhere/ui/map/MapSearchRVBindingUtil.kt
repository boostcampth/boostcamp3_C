package kr.co.connect.boostcamp.livewhere.ui.map

import android.content.Intent
import android.net.Uri
import android.widget.LinearLayout
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.BindingAdapter
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
        intent.putExtra("markerInfo",markerInfo)
        context.startActivity(intent)
    }

}