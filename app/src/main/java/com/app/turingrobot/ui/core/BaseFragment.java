package com.app.turingrobot.ui.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.app.turingrobot.core.RobotApi;
import com.app.turingrobot.core.RobotApplication;

/**
 * Created by Alpha on 2016/3/26 21:55.
 */
public class BaseFragment extends Fragment {

    protected RobotApplication _application;

    protected RobotApi.ApiService _apiService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _application = RobotApplication.getInstance();
        _apiService = _application.getService();
    }

}
