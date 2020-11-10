package com.jingpai.pos.customer.activity.census.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.bean.HistoryInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86173
 * 新页面历史记录 无跳转
 */
public class HistoryInfoAdapter extends RecyclerView.Adapter<HistoryInfoAdapter.HistoryInfoViewHolder> {
    private Context mContext;
    private List<HistoryInfoBean> historyInfoBeans;

    String name;
    String shenFen;
    String createTime;
    String censusName;

    public HistoryInfoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public HistoryInfoAdapter(Context mContext, List<HistoryInfoBean> historyInfoBeans) {
        this.mContext = mContext;
        historyInfoBeans=new ArrayList<>();
        this.historyInfoBeans = historyInfoBeans;
    }



    @NonNull
    @Override
    public HistoryInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        HistoryInfoViewHolder holder = new HistoryInfoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryInfoViewHolder holder, int position) {
        if (historyInfoBeans.size() <= 0 || position > historyInfoBeans.size() || historyInfoBeans.get(position) ==null) return;
        name = historyInfoBeans.get(position).getName()==null?"":historyInfoBeans.get(position).getName();
        shenFen = historyInfoBeans.get(position).getIdentity()==null?"":historyInfoBeans.get(position).getIdentity();
        createTime = historyInfoBeans.get(position).getCreateTime()==null?"":historyInfoBeans.get(position).getCreateTime();
        censusName = historyInfoBeans.get(position).getCensusName()==null?"":historyInfoBeans.get(position).getCensusName();

//        Glide.with(mContext).load(icon).into(holder.iv_icon);
        holder.mTvName.setText(name);
        holder.tv_census_name.setText(censusName);
        holder.mTvTime.setText(createTime);
        holder.mTvType.setText(shenFen);

    }

    @Override
    public int getItemCount() {
        return historyInfoBeans == null ? 0 : historyInfoBeans.size();
    }

    public void setData(List<HistoryInfoBean> list){
        historyInfoBeans.clear();
        historyInfoBeans.addAll(list);
        notifyDataSetChanged();
    }

    public class HistoryInfoViewHolder extends RecyclerView.ViewHolder{
        private TextView mTvName;
        private TextView tv_census_name;
        private ImageView mIvSex;
        private TextView mTvType;
        private TextView mTvStatus;
        private TextView mTvTime;
        private ImageView mIvJiantou;



        public HistoryInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tv_name);
            tv_census_name = itemView.findViewById(R.id.tv_census_name);
            mIvSex = itemView.findViewById(R.id.iv_sex);
            mTvType = itemView.findViewById(R.id.tv_type);
            mTvStatus = itemView.findViewById(R.id.tv_status);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mIvJiantou = itemView.findViewById(R.id.iv_jiantou);
        }
    }
}
