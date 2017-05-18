package com.app.turingrobot.core.dagger.component


import com.app.turingrobot.core.dagger.module.AppModule
import com.app.turingrobot.ui.core.BaseActivity
import com.app.turingrobot.ui.core.BaseFragment

import javax.inject.Singleton

import dagger.Component

/*
 * Created by sword on 2017/3/27.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(activity: BaseActivity)

    fun inject(fragment: BaseFragment)

}
