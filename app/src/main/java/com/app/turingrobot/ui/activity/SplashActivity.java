package com.app.turingrobot.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.app.turingrobot.R;
import com.app.turingrobot.ui.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * APP 启动页
 * Created by Alpha on 2016/3/27 20:00.
 */
public class SplashActivity extends BaseActivity {

    private static final long DEFAULT_DELAY_TIME = 4000;

    @BindView(R.id.logo)
    ImageView logo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        logo.animate().scaleX(3f).scaleY(3f).setDuration(3000).start();

        new Handler().postDelayed(() -> {
            MainActivity.start(SplashActivity.this, MainActivity.class, null);
            SplashActivity.this.finish();
        }, DEFAULT_DELAY_TIME);
    }
}
