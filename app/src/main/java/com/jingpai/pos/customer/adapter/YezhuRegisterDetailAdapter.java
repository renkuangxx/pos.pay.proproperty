package com.jingpai.pos.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jingpai.pos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86173
 */
public class YezhuRegisterDetailAdapter extends RecyclerView.Adapter<YezhuRegisterDetailAdapter.YezhuRegisterDetailViewHolder> {
    private Context mContext;
    private List<String> list;

    public YezhuRegisterDetailAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        list = new ArrayList<>();
        this.list = list;
    }

    public void setData(List<String> list1){
        list.clear();
        list.addAll(list1);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public YezhuRegisterDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_yezhu, parent, false);
        YezhuRegisterDetailViewHolder holder = new YezhuRegisterDetailViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull YezhuRegisterDetailViewHolder holder, int position) {
        if (list.size() <= 0 || position > list.size() || list.get(position) ==null) return;
        String url = list.get(position);
        Glide.with(mContext)
                .load(url)
                .into(holder.iv_hetong);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class YezhuRegisterDetailViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_hetong;
        public YezhuRegisterDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_hetong = itemView.findViewById(R.id.iv_hetong);
        }
    }
}
