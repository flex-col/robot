package com.app.turingrobot.core.dagger.component;

import com.app.turingrobot.core.dagger.module.AModule;
import com.app.turingrobot.core.dagger.module.FModule;
import com.app.turingrobot.core.dagger.scope.ActivityScope;
import com.app.turingrobot.ui.core.BaseFragment;

import dagger.Component;

/**
 * Created by sword on 2017/3/27.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = FModule.class)
public interface FragmentComponent {

    void inject(BaseFragment baseFragment);

}
