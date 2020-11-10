package com.jingpai.pos.customer.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.show.Home.ShowImgActivity;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.bean.CommunityFunnyBean;

import java.util.List;

/*
 * function:
 */
public class CommunityFunnyAdapter extends BaseQuickAdapter<CommunityFunnyBean, BaseViewHolder> {

    public CommunityFunnyAdapter(int layoutResId, @Nullable List<CommunityFunnyBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, CommunityFunnyBean item) {
        ImageView imageView = helper.getView(R.id.iv_bg);
        if (helper.getAdapterPosition()==0){
            Glide.with(MyApplication.getContext()).asBitmap().load(R.mipmap.item_community_funny1)
                    .into(imageView);
        }else{
            Glide.with(MyApplication.getContext()).asBitmap().load(R.mipmap.item_community_funny2)
                    .into(imageView);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowImgActivity.class);
                if (helper.getAdapterPosition()==0){
                    intent.putExtra("index",1);
                }else{
                    intent.putExtra("index",2);
                }
                mContext.startActivity(intent);
            }
        });
    }

    public interface OnItemClick {
        void onItemClick(String promotionId);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick OnItemClick) {
        this.onItemClick = OnItemClick;
    }

}