package com.zhaoyuanjie.adphoneblocker

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 操作SharedPreference的类
 * Created by zhaoyuanjie on 16/3/7.
 */
class AppPreferences(private val context: Context) {
    val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var whiteList by PreferenceDelegates.string("[]")

    fun getWhiteList(): MutableSet<String> {
        return Gson().fromJson(whiteList, HashSet<String>().javaClass)
    }

    fun addNumberToWhiteList(number: String) {
        val list = getWhiteList()
        list.add(number)
        whiteList = Gson().toJson(list)
    }
}

object PreferenceDelegates {
    fun string(defaultValue: String? = null): ReadWriteProperty<AppPreferences, String?> {
        return PrefString(defaultValue)
    }
}

private class PrefString(private val defaultValue: String?) : ReadWriteProperty<AppPreferences, String?> {
    override fun getValue(thisRef: AppPreferences, property: KProperty<*>): String? {
        return thisRef.preferences.getString(property.name, defaultValue)
    }

    override fun setValue(thisRef: AppPreferences, property: KProperty<*>, value: String?) {
        thisRef.preferences.edit().putString(property.name, value).apply()
    }
}