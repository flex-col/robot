package com.app.turingrobot.multitype.base

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * ClassName: MultiTypePresenterImpl
 *
 *
 * Author: blades
 *
 *
 * Des: MultiTypePresenterImpl
 *
 *
 * CreateTime: 2017/1/3 11:15
 *
 *
 * UpdateTime: 2017/1/3 11:15
 *
 *
 * GitHub: https://github.com/AlphaKnife
 */

class MultiTypePresenterImpl private constructor(builder: MultiTypePresenterImpl.Builder) : MultiTypePresenter {

    val viewModels: List<ViewModel>?

    private val holders: SparseArray<ViewHolderPresenter>

    init {
        viewModels = builder.viewModels
        holders = builder.holders
    }

    override fun createViewHolder(inflater: LayoutInflater, group: ViewGroup, type: Int): BaseViewHolder {
        return holders.get(type).createViewHodler(group, inflater)
    }

    override fun bindViewHolder(holder: BaseViewHolder, position: Int) {
        val model = viewModels!![position]

        val presenter = holders.get(model.viewTypeId())

        presenter?.onBind(holder, model)
    }

    override fun unBind(holder: BaseViewHolder) {
        holder.unBind()
    }

    override fun getViewType(position: Int): Int {
        return if (viewModels == null) 0 else viewModels[position].viewTypeId()
    }

    override fun getItemId(position: Int): Long {
        return if (viewModels == null) 0 else viewModels[position].id()
    }


    class Builder {

        var viewModels: List<ViewModel>? = null

        val holders: SparseArray<ViewHolderPresenter> = SparseArray()


        fun withViewModels(value: List<ViewModel>): Builder {
            viewModels = value
            return this
        }

        fun addHolders(value: ViewHolderPresenter): Builder {
            checkNotNull(holders) { "worker must not be null" }
            if (this.holders.get(value.type()) != null) {
                throw IllegalArgumentException("worker type has exits")
            }
            this.holders.put(value.type(), value)
            return this
        }

        fun build(): MultiTypePresenterImpl {
            return MultiTypePresenterImpl(this)
        }
    }

    companion object {

        fun newBuilder(): Builder {
            return Builder()
        }
    }
}
