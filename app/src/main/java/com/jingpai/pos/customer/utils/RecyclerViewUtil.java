package com.jingpai.pos.customer.utils;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewUtil {

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

}
