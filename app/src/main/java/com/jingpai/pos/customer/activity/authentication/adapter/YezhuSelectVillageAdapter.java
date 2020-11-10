package com.jingpai.pos.customer.activity.authentication.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.authentication.activity.YezhuRegisterSearchLoudongActivity;
import com.jingpai.pos.customer.bean.CommunityByCityNameBean;
import com.jingpai.pos.customer.mvp.presenter.LifePresenter;
import com.jingpai.pos.customer.views.UpdataInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间: 2020/2/23
 * 功能: 业主认证 选择小区list
 */
public class YezhuSelectVillageAdapter extends RecyclerView.Adapter<YezhuSelectVillageAdapter.ItemViewHolder> {
    private Context mContext;
    private List<CommunityByCityNameBean.PageBean.RecordsBean> listBeans =new ArrayList<>();
    LifePresenter lifePresenter;
    private UpdataInterface updataInterface;

    public YezhuSelectVillageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public YezhuSelectVillageAdapter(Context mContext, List<CommunityByCityNameBean.PageBean.RecordsBean> listBeans1) {
        this.mContext = mContext;
        listBeans = new ArrayList<>();
        this.listBeans = listBeans1;
    }

    @NonNull
    @Override
    public YezhuSelectVillageAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.village_item_yezhu, parent, false);
        YezhuSelectVillageAdapter.ItemViewHolder holder = new YezhuSelectVillageAdapter.ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull YezhuSelectVillageAdapter.ItemViewHolder holder, int position) {
        if (listBeans.size() <= 0 || position > listBeans.size() || listBeans.get(position) == null)
            return;
        int id = listBeans.get(position).getId();
        holder.tv_village_item.setText(TextUtils.isEmpty(listBeans.get(position).getName())?"":listBeans.get(position).getName());
        holder.iv_selected.setVisibility(View.GONE);
        holder.rl_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                holder.iv_selected.setVisibility(View.VISIBLE);
                Intent intent = new Intent(mContext, YezhuRegisterSearchLoudongActivity.class);
                intent.putExtra("communityId",id);
                intent.putExtra("info",TextUtils.isEmpty(listBeans.get(position).getName())?"":listBeans.get(position).getName());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listBeans == null ? 0 : listBeans.size();
    }

    public void setData(List<CommunityByCityNameBean.PageBean.RecordsBean> list) {
        if (list != null) {
            listBeans.clear();
            listBeans.addAll(list);
            notifyDataSetChanged();
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_selected;
        private TextView tv_village_item,tv_distance;
        private RelativeLayout rl_parent;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_selected = itemView.findViewById(R.id.iv_selected);
            tv_village_item = itemView.findViewById(R.id.tv_village_item);
            tv_distance = itemView.findViewById(R.id.tv_distance);
            rl_parent = itemView.findViewById(R.id.rl_parent);

        }
    }
}