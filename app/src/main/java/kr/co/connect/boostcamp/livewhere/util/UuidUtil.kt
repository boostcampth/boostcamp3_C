package kr.co.connect.boostcamp.livewhere.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.telephony.TelephonyManager
import java.io.UnsupportedEncodingException
import java.util.*


@SuppressLint("MissingPermission", "HardwareIds")
fun generateUuid(context: Context): String? {
    val deviceUUID: UUID
    val androidUniqueID = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    try {
        deviceUUID = if (androidUniqueID != "") {
            UUID.nameUUIDFromBytes(androidUniqueID.toByteArray(Charsets.UTF_8))
        } else {
            val anotherUniqueID = (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId
            if (anotherUniqueID != null) {
                UUID.nameUUIDFromBytes(anotherUniqueID.toByteArray(Charsets.UTF_8))
            } else {
                UUID.randomUUID()
            }
        }
    } catch (e: UnsupportedEncodingException) {
        throw RuntimeException(e)
    }
    return deviceUUID.toString()
}