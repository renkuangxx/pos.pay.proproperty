package com.jingpai.pos.customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.LifeListActivity;
import com.jingpai.pos.customer.bean.ContentCateVos;
import com.jingpai.pos.customer.mvp.presenter.LifePresenter;
import com.jingpai.pos.customer.utils.LocalCache;

import java.util.ArrayList;
import java.util.List;

public class ItemStyleAdapter extends RecyclerView.Adapter<ItemStyleAdapter.ItemStyleViewHolder> {
    private Context mContext;
    private List<ContentCateVos> listBeans;
    LifePresenter lifePresenter;
    int styleContentCateId;

    public ItemStyleAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public ItemStyleAdapter(Context mContext, List<ContentCateVos> listBeans1,LifePresenter lifePresenter) {
        this.mContext = mContext;
        listBeans=new ArrayList<>();
        listBeans = listBeans1;
        this.lifePresenter = lifePresenter;
    }


    @Override
    public ItemStyleAdapter.ItemStyleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_style_recommond, parent, false);
        ItemStyleAdapter.ItemStyleViewHolder holder = new ItemStyleAdapter.ItemStyleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemStyleViewHolder holder, int position) {
        if (listBeans.size() <= 0 || position > listBeans.size() || listBeans.get(position) ==null) return;
        String name = TextUtils.isEmpty(listBeans.get(position).getName())?"":listBeans.get(position).getName();
        String frontUrl = TextUtils.isEmpty(listBeans.get(position).getCateImages())?"":listBeans.get(position).getCateImages();
        styleContentCateId = listBeans.get(position).getId();

        Glide.with(mContext)
                .load(frontUrl)
                .apply(new RequestOptions()
                .placeholder(R.mipmap.touxiang)
                .fallback(R.mipmap.touxiang)
                .error(R.mipmap.touxiang))
                .into(holder.ivPic);

        holder.tvTitle.setText(name);
        holder.ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(mContext, LifeListActivity.class);
                intent4.putExtra("contentCateId", listBeans.get(position).getId());
                intent4.putExtra("currentUserId", TextUtils.isEmpty(LocalCache.getUserId()) ? "" : LocalCache.getUserId());
                intent4.putExtra("name", name);
                mContext.startActivity(intent4);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    public void setData(List<ContentCateVos> list){
        if (list==null)return;
        listBeans.clear();
        listBeans.addAll(list);
        notifyDataSetChanged();
    }

    public class ItemStyleViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivPic;
        private TextView tvTitle;


        public ItemStyleViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pic);
            tvTitle =itemView.findViewById(R.id.tv_title);

        }
    }
}