package com.jingpai.pos.views;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.component.dialog.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class TipsDialog extends BaseDialog {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.tv_tip)
    TextView tvTip;

    private String mTitle;

    public TipsDialog(@NonNull Context context) {
        super(context);
    }

    public TipsDialog(@NonNull Context context, String title) {
        super(context);
        mTitle = title;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_tips;
    }

    @Override
    public void init() {
        setCanceledOnTouchOutside(false);
        tvTitle.setText(mTitle);
        initLayoutParamsForCenter();
        if (TextUtils.isEmpty(mTitle)) {
            return;
        }
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setTip(String tip) {
        tvTip.setText(tip);
    }

    public void setOnOkClick(View.OnClickListener click) {
        tvOk.setOnClickListener(click);
    }

    @OnClick(R.id.tv_cancel)
    public void onViewClicked() {
        dismiss();
    }

}
