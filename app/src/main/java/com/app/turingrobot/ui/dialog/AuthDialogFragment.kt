package com.app.turingrobot.ui.dialog

import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.app.turingrobot.R

import com.app.turingrobot.helper.UMHelper
import com.app.turingrobot.helper.event.AuthEvent
import com.app.turingrobot.utils.RxBus
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.bean.SHARE_MEDIA
import kernel.colorRes
import kernel.toast

/**
 * Created by sword on 2017/5/17.
 */

class AuthDialogFragment : DialogFragment() {


    private var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.layout_auth, container, false)
        this.rootView = root
        initView()
        return root
    }

    private fun initView() {
        rootView?.setOnClickListener {
            UMHelper.authQQ(activity, listener)
        }
    }

    private val listener = object : UMAuthListener {
        override fun onComplete(p0: SHARE_MEDIA?, p1: Int, p2: MutableMap<String, String>) {
            "授权成功".toast()
            dismissAllowingStateLoss()
            /**
             * 通知主界面
             */
            RxBus.send(AuthEvent(p2))
        }

        override fun onCancel(p0: SHARE_MEDIA?, p1: Int) {
            "授权取消".toast()
        }

        override fun onError(p0: SHARE_MEDIA?, p1: Int, p2: Throwable?) {
            "授权失败".toast()
        }

        override fun onStart(p0: SHARE_MEDIA?) {
            "开始授权".toast()
        }
    }

    companion object {

        fun newInstance(): AuthDialogFragment {
            return AuthDialogFragment()
        }
    }

}
