package com.jingpai.pos.customer.di.component;
/**
 * Create by liujinheng
 * date 2020/5/18
 * function
 */
import android.app.Activity;
import android.content.Context;

import com.jingpai.pos.activity.MainActivity;
import com.jingpai.pos.activity.login.LoginActivity;
import com.jingpai.pos.customer.di.module.ActivityModule;
import com.jingpai.pos.customer.di.scope.ContextLife;
import com.jingpai.pos.customer.di.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();
    Activity getActivity();

    void inject(LoginActivity activity);
//    void inject(CommanderListActivity activity);
    void inject(MainActivity activity);


}
