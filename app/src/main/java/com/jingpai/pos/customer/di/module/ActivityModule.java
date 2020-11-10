package com.jingpai.pos.customer.di.module;
/**
 * Create by liujinheng
 * date 2020/5/18
 * function
 */
import android.app.Activity;
import android.content.Context;

import com.jingpai.pos.customer.di.scope.ContextLife;
import com.jingpai.pos.customer.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }
}
