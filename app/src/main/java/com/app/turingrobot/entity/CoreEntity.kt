package com.app.turingrobot.entity

/**
 * Created by Alpha on 2016/3/26 22:56.
 */
data class CoreEntity(var code: Int = 0, var text: String? = "", var isTarget: Boolean = true, var time: Long = 0) {

    constructor() : this(0, "", true, 0)
}