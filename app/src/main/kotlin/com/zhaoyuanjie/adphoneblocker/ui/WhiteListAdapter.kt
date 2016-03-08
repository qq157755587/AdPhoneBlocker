package com.zhaoyuanjie.adphoneblocker.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhaoyuanjie.adphoneblocker.R
import kotlinx.android.synthetic.main.white_list_item_view.view.*

/**
 * 白名单列表的adapter
 * Created by zhaoyuanjie on 16/3/7.
 */
class WhiteListAdapter(val list: MutableList<String>, val listener: WhiteListListener?): RecyclerView.Adapter<WhiteListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder? {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.white_list_item_view, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        val number = list[position]
        vh.itemView.number.text = number
        vh.itemView.setOnLongClickListener {
            listener?.onLongClick(number, position)
            true;
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) { }

    interface WhiteListListener {

        fun onLongClick(number: String, position: Int)
    }
}