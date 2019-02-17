package kr.co.connect.boostcamp.livewhere.ui.map

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModel

interface OnMapSearchClickListener{
    fun onLaunchUrl(context: Context, url:String)
}

class MapSearchRVViewModel : ViewModel(),OnMapSearchClickListener{
    override fun onLaunchUrl(context: Context, url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
}