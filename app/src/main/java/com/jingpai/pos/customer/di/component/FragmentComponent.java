package com.jingpai.pos.customer.di.component;
/**
 * Create by liujinheng
 * date 2020/5/18
 * function
 */
import android.app.Activity;
import android.content.Context;

import com.jingpai.pos.customer.di.module.FragmentModule;
import com.jingpai.pos.customer.di.scope.ContextLife;
import com.jingpai.pos.customer.di.scope.PerFragment;

import dagger.Component;


@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();
//    void inject(CloudPanFragment fragment);
;

}
