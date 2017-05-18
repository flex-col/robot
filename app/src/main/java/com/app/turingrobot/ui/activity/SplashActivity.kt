package com.app.turingrobot.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView

import com.app.turingrobot.R
import com.app.turingrobot.ui.core.BaseActivity


/**
 * APP 启动页
 * Created by Alpha on 2016/3/27 20:00.
 */
class SplashActivity : BaseActivity() {

    val logo by lazy { findViewById(R.id.logo) as ImageView }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        logo.animate().scaleX(3f).scaleY(3f).setDuration(3000).start()

        Handler().postDelayed({
            BaseActivity.start(this@SplashActivity, MainActivity::class.java, null)
            finish()
        }, DEFAULT_DELAY_TIME)
    }

    companion object {
        private val DEFAULT_DELAY_TIME: Long = 4000
    }
}
