package com.app.turingrobot.core.dagger.component;

import com.app.turingrobot.core.dagger.module.AModule;
import com.app.turingrobot.core.dagger.scope.ActivityScope;
import com.app.turingrobot.ui.core.BaseActivity;

import dagger.Component;

/**
 * Created by sword on 2017/3/27.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = AModule.class)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);

}
