package com.jingpai.pos.customer.di.component;
/**
 * Create by liujinheng
 * date 2020/5/18
 * function
 */
import android.content.Context;

import com.jingpai.pos.customer.di.module.ServiceModule;
import com.jingpai.pos.customer.di.scope.ContextLife;
import com.jingpai.pos.customer.di.scope.PerService;

import dagger.Component;


@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
