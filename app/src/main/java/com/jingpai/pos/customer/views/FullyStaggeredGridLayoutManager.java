package com.jingpai.pos.customer.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Create by liujinheng
 * date 2020/5/22
 * function
 */
public class FullyStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

    public FullyStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FullyStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
