package com.jingpai.pos.customer.custom;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;

import im.delight.android.webview.AdvancedWebView;

public class MyWebView extends AdvancedWebView {
    public MyWebView(Context context) {
        super(getFixedContext(context));
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(getFixedContext(context), attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(getFixedContext(context), attrs, defStyleAttr);
    }
    public static Context getFixedContext(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return context.createConfigurationContext(new Configuration());
        } else {
            return context;
        }
    }

}
