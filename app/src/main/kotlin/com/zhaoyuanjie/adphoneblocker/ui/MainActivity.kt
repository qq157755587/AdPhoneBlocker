package com.zhaoyuanjie.adphoneblocker.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zhaoyuanjie.adphoneblocker.R
import com.zhaoyuanjie.adphoneblocker.util.AppPreferences
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主界面
 * Created by zhaoyuanjie on 15/12/26.
 */
class MainActivity: AppCompatActivity(), WhiteListAdapter.WhiteListListener {

    private val pref by lazy { AppPreferences(this) }
    private lateinit var mAdapter: WhiteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), 0)
        } else {
            permission_state.setText(R.string.permission_granted)
            showWhiteList()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permission_state.setText(R.string.permission_granted)
            showWhiteList()
        } else {
            permission_state.setText(R.string.permission_denied)
        }
    }

    override fun onLongClick(number: String, position: Int) {
        AlertDialog.Builder(this)
                .setMessage(getString(R.string.confirm_remove_from_white_list, number))
                .setPositiveButton(android.R.string.yes, { dialog, which ->
                    pref.removeNumberFromWhiteList(number)
                    mAdapter.list.remove(number)
                    recycler_view.adapter.notifyItemRemoved(position)
                })
                .setNegativeButton(android.R.string.no, null)
                .show()
    }

    private fun showWhiteList() {
        val list = pref.getWhiteList()
        if (list.size > 0) {
            mAdapter = WhiteListAdapter(list, this)
            recycler_view.layoutManager = LinearLayoutManager(this)
            recycler_view.adapter = mAdapter
            recycler_view.visibility = View.VISIBLE
            supportActionBar?.setSubtitle(R.string.white_list)
        }
    }
}