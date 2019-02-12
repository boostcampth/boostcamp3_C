package kr.co.connect.boostcamp.livewhere.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface SharedPreference {
    var uuid:String?
}

class SharedPreferenceStorage(context: Context):SharedPreference {
    private val prefs = context.applicationContext.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    override var uuid: String? by UuidPreference(prefs, PREF_UUID, null)

    companion object {
        const val PREF = "pref"
        const val PREF_UUID = "pref_uuid"
    }
}

class UuidPreference(
    private val pref: SharedPreferences,
    private val name: String,
    private val defaultValue: String?
):ReadWriteProperty<Any,String?> {


    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return pref.getString(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        pref.edit { putString(name, value) }
    }
}