package com.app.turingrobot.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.transition.TransitionManager
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK

import com.app.turingrobot.R
import com.app.turingrobot.app.App
import com.app.turingrobot.entity.user.User
import com.app.turingrobot.extra.bindView
import com.app.turingrobot.helper.SpfHelper
import com.app.turingrobot.helper.UMHelper
import com.app.turingrobot.helper.event.AuthEvent
import com.app.turingrobot.ui.core.BaseActivity
import com.app.turingrobot.ui.dialog.AuthDialogFragment
import com.app.turingrobot.ui.fragment.chat.ChatFragment
import com.app.turingrobot.utils.GlideUtils
import com.app.turingrobot.utils.RxBus
import com.app.turingrobot.utils.StatusBarUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.tencent.bugly.Bugly
import com.umeng.socialize.UMShareAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_auth.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.tb_munion_aditem.*
import java.io.InputStream
import cn.smssdk.SMSSDK.RESULT_COMPLETE
import cn.smssdk.gui.RegisterPage
import com.app.turingrobot.entity.params.user.UserParams
import com.app.turingrobot.extra.toBase64
import com.app.turingrobot.extra.toast
import com.app.turingrobot.helper.rx.RxResultHelper
import java.nio.charset.Charset


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle

    var chatFragment: ChatFragment? = null

    var imgHeader: ImageView? = null


    var tv_name: TextView? = null

    var tv_signature: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //注册Glide请求为OkHttp
        Glide.get(this).register(GlideUrl::class.java, InputStream::class.java,
                OkHttpUrlLoader.Factory(okHttp))

        Bugly.init(applicationContext, "408e519c80", false)

        StatusBarUtil.setColorForDrawerLayout(this, drawer_layout, ContextCompat
                .getColor(this, R.color.colorPrimary))

        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        initFragments()

        imgHeader = nav_view.getHeaderView(0).findViewById(R.id.imgHeader) as ImageView
        tv_name = nav_view.getHeaderView(0).findViewById(R.id.tv_name) as TextView
        tv_signature = nav_view.getHeaderView(0).findViewById(R.id.tv_signature) as TextView

        imgHeader?.setOnClickListener {
            if (App.getUser() == null) {
                AuthDialogFragment.newInstance()
                        .show(fragmentManager, AuthDialogFragment::class.java.simpleName)
            }
        }

        shwoAndRegister(App.getUser())

        register()
    }

    fun register() {

        mDisp.add(RxBus.registerEvent(AuthEvent::class.java)
                .subscribe({
                    val user = User.parseUser(it.map)
                    SpfHelper.toJsonSave(user)
                    shwoAndRegister(user)
                }, {
                    register()
                }))

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("SetTextI18n")
    private fun shwoAndRegister(user: User?) {
        user?.let {
            GlideUtils.displayCircleHeader(imgHeader, it.iconurl)
            tv_name?.text = it.name
            tv_signature?.text = it.province + " " + it.city
            UMHelper.addAlias(it.uid)
        }
    }


    private fun initFragments() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        if (chatFragment == null) {
            chatFragment = ChatFragment.newInstance()
            transaction.add(R.id.fm_container, chatFragment)
        }
        transaction.commit()
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.nav_chat) {
            //聊天
        } else if (id == R.id.nav_share) {
            //分享
            val textIntent = Intent(Intent.ACTION_SEND)
            textIntent.type = "text/plain"
            textIntent.putExtra(Intent.EXTRA_TEXT, "http://shouji.baidu.com/software/7319838.html")
            startActivity(Intent.createChooser(textIntent, "分享"))
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
