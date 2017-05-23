package com.app.turingrobot.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.app.turingrobot.R
import com.app.turingrobot.app.App
import com.app.turingrobot.entity.CoreEntity
import com.app.turingrobot.utils.GlideUtils
import com.app.turingrobot.utils.TimeUtil

import java.util.ArrayList


/*
 * Created by Alpha on 2016/3/26 23:03.
 */
class ChatAdapter(private val mContext: Context) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private val mDatas = ArrayList<CoreEntity>()

    fun setList(list: List<CoreEntity>) {
        mDatas.clear()
        mDatas.addAll(list)
        notifyDataSetChanged()
    }


    fun addData(entity: CoreEntity, recyclerView: RecyclerView) {
        mDatas.add(entity)
        notifyItemRangeChanged(itemCount, itemCount + 1)
        recyclerView.scrollToPosition(itemCount - 1)
    }

    override fun getItemViewType(position: Int): Int {
        if (mDatas[position].isTarget) {
            return TARGET
        } else {
            return MINE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == TARGET) {
            return ViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_type_chat_txt_target, parent, false))
        } else {
            return ViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_type_chat_txt, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (_, text, isTarget, time) = mDatas[position]

        holder.tvContent.text = text

        holder.tvTime.text = TimeUtil.getHourMin(time)

        if (isTarget) {
            GlideUtils.displayCircleHeader(holder.frescoAvatar, "http://bbs.umeng.com/uc_server/avatar.php?uid=34&size=small")
        } else {
            GlideUtils.displayCircleHeader(holder.frescoAvatar, App.getUser()!!.iconurl)
        }

    }

    override fun getItemCount(): Int {
        return mDatas.size
    }


    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        val tvTime by lazy { itemView.findViewById(R.id.tv_time) as TextView }

        val frescoAvatar by lazy { itemView.findViewById(R.id.fresco_avatar) as ImageView }

        val tvContent by lazy { itemView.findViewById(R.id.tv_content) as TextView }

    }

    companion object {

        //接收到的消息
        const val TARGET = 0
        //发送的消息
        const val MINE = 1
    }
}
