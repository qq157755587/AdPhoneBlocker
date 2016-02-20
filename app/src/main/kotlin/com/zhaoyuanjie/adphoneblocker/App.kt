package com.zhaoyuanjie.adphoneblocker

import android.app.Application
import android.content.pm.PackageManager

/**
 * Created by zhaoyuanjie on 16/2/20.
 */
class App:Application() {

    override fun onCreate() {
        super.onCreate()

        val appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        Const.API_KEY = appInfo.metaData.getString("API_KEY")
    }
}