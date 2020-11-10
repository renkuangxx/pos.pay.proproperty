package com.jingpai.pos.customer.views;

/**
 * Create by liujinheng
 * date 2020/7/30
 * function
 */

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import im.delight.android.webview.AdvancedWebView;

/**
 * 主要解决viewPager嵌套webView横向滚动问题
 */

public class ScrollConflictWebView extends AdvancedWebView {

    private boolean isScrollX = false;

    public ScrollConflictWebView(Context context) {
        super(getFixedContext(context));
    }

    public ScrollConflictWebView(Context context, AttributeSet attrs) {
        super(getFixedContext(context), attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount()== 1) { //单指操作
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isScrollX = false;
                    //事件由webview处理
                    getParent().getParent()
                            .requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    //嵌套Viewpager时
                    getParent().getParent()
                            .requestDisallowInterceptTouchEvent(!isScrollX);
                    break;
                default:
                    getParent().getParent()
                            .requestDisallowInterceptTouchEvent(false);
            }
        } else { //双指操作
            //使webview可以双指缩放（前提是webview必须开启缩放功能，并且加载的网页也支持缩放）
            getParent().getParent().
                    requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(event);
    }

    //当webview滚动到边界时执行
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        isScrollX = clampedX;
    }

    public static Context getFixedContext(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return context.createConfigurationContext(new Configuration());
        } else {
            return context;
        }
    }
}
