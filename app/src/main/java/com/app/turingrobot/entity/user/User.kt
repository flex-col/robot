package com.app.turingrobot.entity.user

import com.app.turingrobot.app.App

/**
 * Created by sword on 2017/5/17.
 */
class User(var uid: String?, var name: String?, var accessToken: String?, var expiration: String?, var gender: String?,
           var iconurl: String, var is_yellow_year_vip: String?, var yellow_vip_level: String?, var city: String?, var province: String?) {


    companion object {

        @JvmStatic
        fun parseUser(map: Map<String, String>): User {
            var user = App.getUser()
            if (user == null) {
                user = User(map["uid"], map["name"], map["accessToken"],
                        map["expiration"], map["gender"], map["iconurl"]!!
                        , map["is_yellow_year_vip"], map["yellow_vip_level"], map["city"], map["province"])
                App.setUserBean(user)
            }
            return user
        }
    }

}