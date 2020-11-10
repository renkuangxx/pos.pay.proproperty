package com.jingpai.pos.customer.views;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created By：jinheng.liu
 * on 2020/9/4
 */
  public class StaggeredDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Context context;
    private int space;
    public StaggeredDividerItemDecoration(Context context, int space) {
        this.context = context;
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        // 特别注意此处，获取item在span中的下标，不能用intposition=parent.getChildAdapterPosition(view);这样拿到的可能是正确顺序的下标，因为如上图所示，顺序不规律，设置居左居右边距就可能紊乱。
        int spanIndex = params.getSpanIndex();//这才是真正的视图可见的第几个顺序的下标，不过不是列表对应的索引，只是视觉位置索引。
        // 下面是中间间隔设置，
        if (spanIndex %2 ==0) {
            outRect.left =0;
            outRect.right =space/2;
        }else {// item为奇数位，设置其左间隔为5dp
            outRect.left =space/2;
            outRect.right =0;
        }
// 下方间隔
        outRect.bottom =space;
    }
}

