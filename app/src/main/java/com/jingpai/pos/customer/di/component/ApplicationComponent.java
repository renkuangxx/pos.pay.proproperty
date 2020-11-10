package com.jingpai.pos.customer.di.component;
/**
 * Create by liujinheng
 * date 2020/5/18
 * function
 */
import android.content.Context;

import com.jingpai.pos.customer.di.module.ApplicationModule;
import com.jingpai.pos.customer.di.scope.ContextLife;
import com.jingpai.pos.customer.di.scope.PerApp;

import dagger.Component;

@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}