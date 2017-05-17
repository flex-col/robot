package com.app.turingrobot.ui.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.app.turingrobot.R;
import com.app.turingrobot.core.RobotService;
import com.app.turingrobot.core.App;
import com.app.turingrobot.core.dagger.component.DaggerActivityComponent;
import com.app.turingrobot.core.dagger.module.AModule;
import com.app.turingrobot.helper.SpfHelper;
import com.app.turingrobot.utils.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Alpha on 2016/3/26 20:56.
 */
public class BaseActivity extends AppCompatActivity {

    protected App mApp = App.get();

    @Inject
    protected RobotService apiService;

    @Inject
    protected SpfHelper mSpf;

    protected CompositeDisposable mDispos = new CompositeDisposable();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        inject();
    }

    private void inject() {
        DaggerActivityComponent.builder()
                .aModule(new AModule())
                .appComponent(mApp.getComponent())
                .build()
                .inject(this);
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
