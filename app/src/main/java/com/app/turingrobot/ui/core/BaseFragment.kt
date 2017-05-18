package com.app.turingrobot.ui.core

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import com.app.turingrobot.app.App
import com.app.turingrobot.core.RobotService
import com.app.turingrobot.helper.SpfHelper

import javax.inject.Inject

/**
 * Created by Alpha on 2016/3/26 21:55.
 */
open class BaseFragment : Fragment() {

    @Inject
    lateinit var apiService: RobotService

    @Inject
    lateinit var mSpf: SpfHelper

    protected var activity: AppCompatActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.activity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    private fun inject() {
        App.component.inject(this)
    }

}
