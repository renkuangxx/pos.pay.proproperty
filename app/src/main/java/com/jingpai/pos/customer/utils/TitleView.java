package com.jingpai.pos.customer.utils;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.jingpai.pos.R;
/*
 * function:
 */
public class TitleView extends FrameLayout {
    private final TextView tvTitle;
    private final TextView tvRightBtn;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.action_bar, this);
        tvTitle = findViewById(R.id.tv_title);
        tvRightBtn = findViewById(R.id.tv_right_btn);
        ImageView back = findViewById(R.id.iv_back);

        back.setOnClickListener(v -> ((Activity) getContext()).finish());
    }

    /**
     * 设置标题	 * @param text
     */
    public void setTitleText(String text) {
        tvTitle.setText(text);
    }

    /**
     * 设置右侧文字	 * @param text
     */
    public void setTvRightBtn(String text) {
        tvRightBtn.setText(text);
    }

    /**
     * 显示右侧文字按钮
     */
    public void showTvRightBtn() {
        tvRightBtn.setVisibility(View.VISIBLE);
    }

}