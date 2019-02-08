package kr.co.connect.boostcamp.livewhere.ui.map

import android.content.Intent
import android.net.Uri
import android.widget.LinearLayout
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.BindingAdapter

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