package com.jingpai.pos.customer.component.dialog;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckTimeDialog extends BaseDialog {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.tv_start_update)
    TextView tvStartUpdate;
    @BindView(R.id.iv_update_close)
    ImageView ivUpdateClose;

    public CheckTimeDialog(@NonNull Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_app_update;
    }

    @Override
    public void init() {
        initLayoutParamsForCenter();
    }

    @OnClick(R.id.iv_update_close)
    public void onViewClicked() {
        dismiss();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void onActivityCreate(){
        LogUtil.e(TAG,"onActivityCreate");
        //下面可以开始检测版本是否需要更新
        //需要更新  show()
        //默认强制更新
        //非强制跟新  ivUpdateClose.setVisibility(View.VISIBLE);
    }

}
