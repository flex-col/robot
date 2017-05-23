package com.app.turingrobot.ui.fragment.chat.model

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.turingrobot.R
import com.app.turingrobot.entity.CoreEntity
import com.app.turingrobot.multitype.base.BaseViewHolder
import com.app.turingrobot.multitype.base.ViewHolderPresenter
import com.app.turingrobot.multitype.base.ViewModel
import com.app.turingrobot.utils.GlideUtils
import com.app.turingrobot.utils.TimeUtil

/*
 * Created by sword on 2017/5/23.
 */
class TextMsgTargetModel(msgData: CoreEntity) : TextMsgModel(msgData) {


    override fun viewTypeId(): Int {
        return viewTypeId
    }


    class DataHolder(@LayoutRes layoutId: Int, inflater: LayoutInflater, parent: ViewGroup) : BaseViewHolder(layoutId, inflater, parent) {

        val tv_content by lazy { itemView.findViewById(R.id.tv_content) as TextView }

        val tv_time by lazy { itemView.findViewById(R.id.tv_time) as TextView }

        val fresco_avatar by lazy { itemView.findViewById(R.id.fresco_avatar) as ImageView }

    }


    class DataPresenterImpl : ViewHolderPresenter {

        override fun onBind(holder: BaseViewHolder, entity: ViewModel) {

            if (holder is DataHolder && entity is TextMsgModel) {

                holder.tv_content.text = entity.data.text

                holder.tv_time.text = TimeUtil.getHourMin(entity.data.time)
                GlideUtils.displayCircleHeader(holder.fresco_avatar, "http://bbs.umeng.com/uc_server/avatar.php?uid=34&size=small")

            }

        }


        override fun createViewHodler(parent: ViewGroup, inflater: LayoutInflater): BaseViewHolder {
            return DataHolder(viewTypeId, inflater, parent)
        }


        override fun type(): Int {
            return viewTypeId
        }
    }

    companion object {

        const val viewTypeId = R.layout.item_type_chat_txt_target

        fun newPresenter(): DataPresenterImpl = DataPresenterImpl()
    }
}