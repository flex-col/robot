package com.app.turingrobot.ui.core;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.app.turingrobot.core.App;
import com.app.turingrobot.core.RobotService;
import com.app.turingrobot.core.dagger.component.DaggerFragmentComponent;
import com.app.turingrobot.core.dagger.module.FModule;
import com.app.turingrobot.helper.SpfHelper;

import javax.inject.Inject;

/**
 * Created by Alpha on 2016/3/26 21:55.
 */
public class BaseFragment extends Fragment {

    protected App mApp = App.get();

    @Inject
    protected RobotService apiService;

    @Inject
    protected SpfHelper mSpf;

    protected AppCompatActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
    }

    private void inject() {
        DaggerFragmentComponent.builder()
                .fModule(new FModule())
                .appComponent(mApp.getComponent())
                .build().inject(this);
    }

}
