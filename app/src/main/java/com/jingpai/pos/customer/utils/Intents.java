package com.jingpai.pos.customer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Intents {

    public static Intents instence;

    public static Intents getInstence() {
        if (null == instence) {
            instence = new Intents();
        }
        return instence;
    }

    private Intents() {

    }
    /**
     * 不带参数的跳转
     * @param fromContext
     * @param cls         泛型
     */
    public void intent(Context fromContext, Class<?> cls) {
        Intent intent = new Intent(fromContext, cls);
        fromContext.startActivity(intent);
    }
    /**
     * 带参数的跳转
     *
     * @param fromContext
     * @param cls         泛型
     */
    public void intent(Context fromContext, Class<?> cls, Bundle bb) {
        Intent intent = new Intent(fromContext, cls);
        intent.putExtras(bb);
        fromContext.startActivity(intent);
    }
    /**
     * 封装 startActivityForResult 无带参数传�? </br>
     * Same PackageUtils calling
     * {@link #startActivityForResult(Activity, Class, int, Bundle)}
     *
     * @param fromClass
     * @param toClass
     * @param requestCode
     */
    public void startActivityForResult(Activity fromClass, Class<?> toClass, int requestCode) {
        startActivityForResult(fromClass, toClass, requestCode, null);
    }
    /**
     * 封装 startActivityForResult 带参数传
     *
     * @param fromClass
     * @param toClass
     * @param requestCode
     * @param data        参数传
     */
    public void startActivityForResult(Activity fromClass, Class<?> toClass, int requestCode, Bundle data) {
        Intent intent = new Intent();
        intent.setClass(fromClass, toClass);
        if (null != data) {
            intent.putExtras(data);
        }
        fromClass.startActivityForResult(intent, requestCode);
    }
    /**
     * 接收参数然后在返回数值
     *
     * @param fromContext 当前的activity
     * @param bb
     * @param RESULT_OK
     */
    public void setResult(Activity fromContext, Bundle bb, int RESULT_OK) {
        Intent intent = new Intent();
        intent.putExtras(bb);
        fromContext.setResult(RESULT_OK, intent);
        fromContext.finish();
    }
}