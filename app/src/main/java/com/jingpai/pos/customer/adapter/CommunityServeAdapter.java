package com.jingpai.pos.customer.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.show.CommunityServeBean;
import com.jingpai.pos.customer.utils.GlideUtils;
import com.jingpai.pos.customer.utils.SystemUtils;
import com.jingpai.pos.utils.ToastUtils;

import java.util.List;

/*
 * function:
 */
public class CommunityServeAdapter extends BaseQuickAdapter<CommunityServeBean.MenuBean, BaseViewHolder> {
    private boolean isVisitor;
    private int[] bg = new int[]{R.drawable.app_shape_change_ffffd5ce,R.drawable.app_shape_change_ffe7dcfa,R.drawable.app_shape_change_ffcdf5ff,R.drawable.app_shape_change_fffaead2};
    public CommunityServeAdapter(int layoutResId, @Nullable List<CommunityServeBean.MenuBean> data,boolean isVisitor) {
        super(layoutResId, data);
        this.isVisitor =isVisitor;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, CommunityServeBean.MenuBean item) {
        ImageView imageView = helper.getView(R.id.iv_community_serve_icon);
        TextView title = helper.getView(R.id.iv_community_serve_title);
        GlideUtils.LoadingRoundedCornersImg(mContext,item.getIcon(),imageView,10);
        title.setText(item.getName());

        View view = helper.getView(R.id.ll_promotion);
        view.setOnClickListener(v -> {
            if (item.getStatus()==0){
                ToastUtils.INSTANCE.showToast("您所在的社区尚未开通该功能");
                return;
            }
            if (isVisitor&&item.getTourisType()==0){
                onItemClick.onItemClick(-1);
                return;
            }
            onItemClick.onItemClick(helper.getAdapterPosition());
        });

        View llBg = helper.getView(R.id.ll_bg);
        int index = helper.getAdapterPosition()%4;
        llBg.setBackground(mContext.getResources().getDrawable(bg[index]));

        View takeView = helper.getView(R.id.takeview);
        ViewGroup.LayoutParams layoutParams1 = takeView.getLayoutParams();
        if (helper.getAdapterPosition()==0){
            layoutParams1.width =  SystemUtils.Dp2Px(mContext,16);
        }else{
            layoutParams1.width =  SystemUtils.Dp2Px(mContext,10);
        }
        takeView.setLayoutParams(layoutParams1);
    }

    public interface OnItemClick {
        void onItemClick(int position);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick OnItemClick) {
        this.onItemClick = OnItemClick;
    }

}