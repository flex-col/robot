package com.app.turingrobot.ui.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.app.turingrobot.R;
import com.app.turingrobot.core.RobotApi;
import com.app.turingrobot.core.RobotApplication;
import com.app.turingrobot.utils.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Alpha on 2016/3/26 20:56.
 */
public class BaseActivity extends AppCompatActivity {

    protected RobotApplication _application;

    protected RobotApi.ApiService _apiService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _application = RobotApplication.getInstance();
        _apiService = _application.getService();

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setStatusBarColor();
    }

    /**
     * 设置状态栏颜色
     */
    protected void setStatusBarColor() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    protected static void start(Context th, Class<?> activity, Bundle bundle) {
        Intent intent = new Intent(th, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        th.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
