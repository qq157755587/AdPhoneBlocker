package com.zhaoyuanjie.adphoneblocker

import android.app.IntentService
import android.content.Intent
import android.support.v4.app.NotificationManagerCompat

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
            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.cancelAll()
        }
    }
}