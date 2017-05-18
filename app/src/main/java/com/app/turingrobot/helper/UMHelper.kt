package com.app.turingrobot.helper

import android.app.Activity
import android.content.Context
import com.app.turingrobot.app.App
import com.app.turingrobot.extra.log
import com.socks.library.KLog
import com.umeng.message.PushAgent
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA

/**
 * Created by sword on 2017/5/17.
 */
class UMHelper private constructor() {

    companion object {

        /**
         * 添加Alias
         */
        @JvmStatic
        fun addAlias(uID: String?) {
            val pushAgent = PushAgent.getInstance(App.get())
            pushAgent.addAlias(uID, "uID", { _, message ->
                "addAlias ---- > message --->  $message   uID ---> $uID".log()
            })
        }

        /**
         * 授权QQ
         */
        @JvmStatic
        fun authQQ(context: Activity, listener: UMAuthListener) {
            UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.QQ, listener)
        }
    }

}