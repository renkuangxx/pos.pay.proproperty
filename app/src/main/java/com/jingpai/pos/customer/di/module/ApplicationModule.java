package com.jingpai.pos.customer.di.module;
/**
 * Create by liujinheng
 * date 2020/5/18
 * function
 */
import android.content.Context;

import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.di.scope.ContextLife;
import com.jingpai.pos.customer.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {
    private MyApplication mApplication;

    public ApplicationModule(MyApplication application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }
}
