package com.app.turingrobot.ui.fragment.chat.model

import android.content.Intent
import android.support.annotation.LayoutRes
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.turingrobot.R
import com.app.turingrobot.entity.CoreEntity
import com.app.turingrobot.multitype.base.BaseViewHolder
import com.app.turingrobot.multitype.base.ViewHolderPresenter
import com.app.turingrobot.multitype.base.ViewModel
import com.app.turingrobot.ui.activity.WebActivity
import com.app.turingrobot.utils.GlideUtils
import com.app.turingrobot.utils.TimeUtil

/*
 * Created by sword on 2017/5/23.
 */
open class LinkModel(var data: CoreEntity) : ViewModel() {


    override fun viewTypeId(): Int {
        return viewTypeId
    }


    class DataHolder(@LayoutRes layoutId: Int, inflater: LayoutInflater, parent: ViewGroup) : BaseViewHolder(layoutId, inflater, parent) {

        val content by lazy { itemView.findViewById<ConstraintLayout>(R.id.content) }

        val title by lazy { itemView.findViewById<TextView>(R.id.title) }

        val tv_time by lazy { itemView.findViewById<TextView>(R.id.tv_time) }

        val tv_content by lazy { itemView.findViewById<TextView>(R.id.tv_content) }

        val fresco_avatar by lazy { itemView.findViewById<ImageView>(R.id.fresco_avatar) }

    }


    class DataPresenterImpl : ViewHolderPresenter {

        override fun onBind(holder: BaseViewHolder, entity: ViewModel) {

            if (holder is DataHolder && entity is LinkModel) {

                holder.title.text = entity.data.text
                holder.tv_content.text = entity.data.url

                holder.tv_time.text = TimeUtil.getHourMin(entity.data.time)

                GlideUtils.displayCircleHeader(holder.fresco_avatar, "http://bbs.umeng.com/uc_server/avatar.php?uid=34&size=small")

                holder.content.setOnClickListener {
                    val intent = Intent(holder.content.context, WebActivity::class.java)
                    intent.putExtra("mUrl", entity.data.url)
                    holder.itemView.context.startActivity(intent)
                }

            }

        }


        override fun createViewHodler(parent: ViewGroup, inflater: LayoutInflater): BaseViewHolder {
            val viewHolder = DataHolder(viewTypeId, inflater, parent)
            return viewHolder
        }


        override fun type(): Int {
            return viewTypeId
        }
    }

    companion object {

        const val viewTypeId = R.layout.item_type_link

        fun newPresenter(): DataPresenterImpl = DataPresenterImpl()
    }


}