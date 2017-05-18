package com.app.turingrobot.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

import com.app.turingrobot.R
import com.app.turingrobot.app.App
import com.app.turingrobot.entity.user.User
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
import kernel.bindView
import java.io.InputStream

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    val toolbar by bindView<Toolbar>(R.id.toolbar)

    val drawer by bindView<DrawerLayout>(R.id.drawer_layout)

    var toggle: ActionBarDrawerToggle? = null

    var chatFragment: ChatFragment? = null

    var img_head: ImageView? = null
    var tv_name: TextView? = null
    var tv_signature: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //注册Glide请求为OkHttp
        Glide.get(this).register(GlideUrl::class.java, InputStream::class.java,
                OkHttpUrlLoader.Factory(okHttp))

        Bugly.init(applicationContext, "408e519c80", false)

        StatusBarUtil.setColorForDrawerLayout(this, findViewById(R.id.drawer_layout) as DrawerLayout, ContextCompat
                .getColor(this, R.color.colorPrimary))

        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle!!)
        toggle?.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        img_head = navigationView.getHeaderView(0).findViewById(R.id.imageView) as ImageView

        tv_name = navigationView.getHeaderView(0).findViewById(R.id.tv_name) as TextView
        tv_signature = navigationView.getHeaderView(0).findViewById(R.id.tv_signature) as TextView

        shwoAndRegister(App.getUser())

        initFragments()

        img_head?.setOnClickListener {
            if (App.getUser() == null) {
                AuthDialogFragment.newInstance()
                        .show(fragmentManager, AuthDialogFragment::class.java.simpleName)
            }
        }

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


    private fun shwoAndRegister(user: User?) {
        user?.let {
            GlideUtils.displayCircleHeader(img_head, user.iconurl!!)
            tv_name?.text = user.name
            tv_signature?.text = user.province + " " + user.city
            UMHelper.addAlias(user.uid)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
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
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
