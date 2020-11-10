package com.jingpai.pos.customer.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.utils.LogUtil;
import com.jingpai.pos.customer.utils.PixelUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static androidx.lifecycle.Lifecycle.Event.ON_DESTROY;
import static androidx.lifecycle.Lifecycle.Event.ON_START;
import static androidx.lifecycle.Lifecycle.Event.ON_STOP;


public abstract class BaseDialog extends Dialog implements LifecycleObserver {
    protected final String TAG=getClass().getSimpleName();
    private Unbinder unbinder;

    // 判断弹窗之前是不是显示状态
    private boolean isShow = false;

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.DialogTheme);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        init();
    }

    @Override
    public void show() {
        super.show();

        if (unbinder==null){
            unbinder = ButterKnife.bind(this);
        }
    }

    public abstract int getLayoutId();

    public abstract void init();

    public void initLayoutParamsForBottom() {
        Window window = this.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 显示在中间
     */
    public void initLayoutParamsForCenter() {
        Window window = this.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View decorView = window.getDecorView();
        PixelUtil pixelUtil = new PixelUtil(getContext());
        decorView.setPadding(pixelUtil.dp2px(50), 0, pixelUtil.dp2px(50), 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder=null;
        }
    }


    /**
     * Activity进入Start的时候根据状态，看是否显示
     */
    @OnLifecycleEvent(ON_START)
    private void onContextStart() {
        if (isShow) {
            LogUtil.e(TAG, "onContextStart");
            show();
            unbinder = ButterKnife.bind(this);
            init();
        }

    }


    /**
     * Activity进入stop的时候消失，保存状态
     */
    @OnLifecycleEvent(ON_STOP)
    private void onContextStop() {
        isShow = isShowing();
        LogUtil.e(TAG, "onContextStop");
        dismiss();
    }
    /**
     * Activity进入Destroy的时候根据状态，看是否显示
     */
    @OnLifecycleEvent(ON_DESTROY)
    private void onContextDestroy() {
        isShow=false;
        dismiss();
    }

}
