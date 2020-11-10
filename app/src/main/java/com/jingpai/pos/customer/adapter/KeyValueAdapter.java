package com.jingpai.pos.customer.adapter;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.custom.KeyValueBean;

public class KeyValueAdapter extends BaseQuickAdapter<KeyValueBean, BaseViewHolder> {

    public KeyValueAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, KeyValueBean item) {
        TextView tvValue = helper.getView(R.id.tv_value);
        helper.setText(R.id.tv_key, item.getKey());
        tvValue.setText(item.getValue());
        if (item.getDrawableLeftId() > 0) {
            Drawable drawableLeft = mContext.getResources().getDrawable(item.getDrawableLeftId());
            tvValue.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableLeft, null, null, null);
        } else {
            tvValue.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        }
        tvValue.setCompoundDrawablePadding(2);
    }


}
