package com.jingpai.pos.customer.di.scope;
/**
 * Create by liujinheng
 * date 2020/5/18
 * function
 */
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
