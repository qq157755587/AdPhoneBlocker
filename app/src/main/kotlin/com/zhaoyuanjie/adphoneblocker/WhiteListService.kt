package com.zhaoyuanjie.adphoneblocker

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent

/**
 * 添加白名单的Service
 * Created by zhaoyuanjie on 16/3/7.
 */
class WhiteListService: IntentService("white_list") {

    private val pref by lazy { AppPreferences(this) }

    override fun onHandleIntent(intent: Intent) {
        val number = intent.getStringExtra("number")
        if (number != null) {
            pref.addNumberToWhiteList(number)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }
    }
}