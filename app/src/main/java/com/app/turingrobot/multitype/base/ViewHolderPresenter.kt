package com.app.turingrobot.multitype.base

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * ClassName: ViewHolderPresenter
 *
 *
 * Author: blades
 *
 *
 * Des: ViewHolderPresenter
 *
 *
 * CreateTime: 2017/1/3 11:43
 *
 *
 */
interface ViewHolderPresenter {
    /**
     * 创建ViewHolder

     * @param parent
     * *
     * @param inflater
     * *
     * @return
     */
    fun createViewHodler(parent: ViewGroup, inflater: LayoutInflater): BaseViewHolder

    fun onBind(holder: BaseViewHolder, entity: ViewModel)

    /**
     * 模型类型

     * @return
     */
    fun type(): Int
}
