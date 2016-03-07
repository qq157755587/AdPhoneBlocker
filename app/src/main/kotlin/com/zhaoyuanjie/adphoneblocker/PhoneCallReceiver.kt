package com.zhaoyuanjie.adphoneblocker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.telephony.TelephonyManager
import retrofit.Call
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit

/**
 * 监听打入电话的receiver
 * Created by zhaoyuanjie on 15/12/26.
 */
class PhoneCallReceiver: BroadcastReceiver() {

    private var call: Call<QueryResult>? = null;
    private val notifyId = 54256;

    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
        if (state == TelephonyManager.EXTRA_STATE_RINGING) {
            if (!inWhiteList(context, number)) {
                queryPhoneInfo(context, number)
            }
        } else {
            call?.cancel()
            call = null
        }
    }

    private fun inWhiteList(context: Context, number: String): Boolean {
        return AppPreferences(context).getWhiteList().contains(number)
    }

    private fun queryPhoneInfo(context: Context, number: String) {
        call = Restful.baiduApi().query(number, true)
        call?.enqueue(object: Callback<QueryResult> {
            override fun onResponse(response: Response<QueryResult>, retrofit: Retrofit) {
                val info = response.body().response[number]
                if (info != null && info.name != null) {
                    showNotification(context, info, number)
                }
            }

            override fun onFailure(t: Throwable) {

            }
        })
    }

    private fun showNotification(context: Context, info: PhoneInfo, number: String) {
        val intent = Intent(context, WhiteListService::class.java)
        intent.putExtra("number", number)
        val pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_action_settings_phone)
                .setContentTitle(info.name)
                .setContentText(context.getString(R.string.marked_count, info.count ?: 1))
                .addAction(R.drawable.ic_stat_content_add_circle_outline,
                        context.getString(R.string.add_to_white_list),
                        pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 不这样弄一下就显示不了heads-up notification
            builder.setVibrate(longArrayOf())
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notifyId, builder.build())
    }
}