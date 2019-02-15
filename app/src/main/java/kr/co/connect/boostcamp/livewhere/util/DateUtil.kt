package kr.co.connect.boostcamp.livewhere.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


object DateUtil {
    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate() : String? {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일")
        return sdf.format(date)
    }
}