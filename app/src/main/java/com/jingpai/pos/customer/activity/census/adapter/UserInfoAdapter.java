package com.jingpai.pos.customer.activity.census.adapter;

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
import com.jingpai.pos.customer.activity.census.OnDeleteClickLister;
import com.jingpai.pos.customer.activity.census.activity.OwnerInfoActivity;
import com.jingpai.pos.customer.activity.census.bean.DataBean;
import com.jingpai.pos.customer.activity.census.bean.PopulationBean;
import com.jingpai.pos.customer.utils.LocalCache;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86173
 * 首页历史记录 部分跳转
 */
public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder> implements View.OnClickListener,OnDeleteClickLister{
    private Context mContext;
    private List<DataBean> historyInfoBeans;
    String name;
    String shenFen;
    String shenFenZhengNum;
    String sex;
    String baiFenBi;
    PopulationBean populationBean;
    private OnItemClickListener mListener;
    private OnDeleteClickLister mDeleteClickListener;

    public UserInfoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public UserInfoAdapter(Context mContext, List<DataBean> historyInfoBeans) {
        this.mContext = mContext;
        historyInfoBeans = new ArrayList<>();
        this.historyInfoBeans = historyInfoBeans;
    }

    @NonNull
    @Override
    public UserInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_front, parent, false);
        UserInfoViewHolder holder = new UserInfoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoViewHolder holder, int position) {
        if (historyInfoBeans.size() <= 0 || position > historyInfoBeans.size() || historyInfoBeans.get(position) ==null) return;
        name = historyInfoBeans.get(position).getName()==null?"":historyInfoBeans.get(position).getName();
        //yezhu
        shenFen = historyInfoBeans.get(position).getIdentity()==null?"":historyInfoBeans.get(position).getIdentity();
//        wanchengdu
        baiFenBi = historyInfoBeans.get(position).getCompleteness()+""==null?"":historyInfoBeans.get(position).getCompleteness()+"";
        //shen fen zheng
        shenFenZhengNum = historyInfoBeans.get(position).getIdPassportNo()==null?"":historyInfoBeans.get(position).getIdPassportNo();

        if ("业主".equals(shenFen)){
            sex = LocalCache.getUserSex() == 1?"男":"女";
        }else{
            sex = historyInfoBeans.get(position).getGender()==null?"":historyInfoBeans.get(position).getGender();
        }


        holder.tvName.setText(name);

        if (baiFenBi.equals("100")){
            //xianshi > yincang yitijiao
            holder.tv_tijiao.setVisibility(View.VISIBLE);
            holder.ivJiantou.setVisibility(View.INVISIBLE);
            holder.tvTime.setVisibility(View.GONE);
            holder.tvStatus.setVisibility(View.GONE);
            holder.rl_big.setOnClickListener(null);
            SlipRight(holder.tv_delete);
        }else{ //跳转
            holder.tv_tijiao.setVisibility(View.GONE);
            holder.ivJiantou.setVisibility(View.VISIBLE);
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvStatus.setVisibility(View.VISIBLE);

            holder.rl_big.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    populationBean = historyInfoBeans.get(position).getPopulation();
                    String id = populationBean.getId()+"";
                    if (!TextUtils.isEmpty(id+"")){
                        LocalCache.saveInfo(id,populationBean);
                    }
                    Intent intent = new Intent(mContext, OwnerInfoActivity.class);
                    intent.putExtra("idNum",id);
                    if (null!=historyInfoBeans.get(position).getIdentity()){
                        intent.putExtra("position", "业主".equals(historyInfoBeans.get(position).getIdentity())?"0":"1");
                    }
                    mContext.startActivity(intent);
                }
            });

            if (!holder.tv_delete.hasOnClickListeners()) {
                holder.tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mDeleteClickListener != null) {
                            mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                        }
                    }
                });
            }
        }
        if (sex.equals("男")){
            holder.ivSex.setBackground(mContext.getDrawable(R.mipmap.man));
//            Glide.with(mContext).load(R.mipmap.man).into(holder.ivSex);
        }else if (sex.equals("女")){
            holder.ivSex.setBackground(mContext.getDrawable(R.mipmap.nv));
//            Glide.with(mContext).load(R.mipmap.nv).into(holder.ivSex);
        }else{
            holder.ivSex.setVisibility(View.GONE);
        }
        holder.tvStatus.setText("已完成"+baiFenBi+"%");
        holder.shenFenZheng.setText(shenFenZhengNum);
        holder.tvType.setText(shenFen);

        holder.tv_delete.setTag(position);

    }
    public void setData(List<DataBean> list){
        historyInfoBeans.clear();
        historyInfoBeans.addAll(list);
        notifyDataSetChanged();
    }
    //禁止右滑菜单
    private void SlipRight(View view){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width=0;
        view.setLayoutParams(layoutParams);
    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public int getItemCount() {
        return historyInfoBeans == null ? 0 : historyInfoBeans.size();
    }

    @Override
    public void onDeleteClick(View view, int position) {

    }

    public class UserInfoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView ivSex;
        private TextView tvType;
        private TextView tvStatus;
        private TextView shenFenZheng;
        private TextView tvTime;
        private TextView tv_delete;
        private TextView tv_tijiao;
        private ImageView ivJiantou;
        private RelativeLayout rl_big;
        public UserInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            ivSex = itemView.findViewById(R.id.iv_sex);
            tvType = itemView.findViewById(R.id.tv_type);
            tvStatus = itemView.findViewById(R.id.tv_status);
            shenFenZheng = itemView.findViewById(R.id.shen_fen_zheng);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivJiantou = itemView.findViewById(R.id.iv_jiantou);
            tv_tijiao = itemView.findViewById(R.id.tv_tijiao);
            rl_big = itemView.findViewById(R.id.rl_big);
            tv_delete = itemView.findViewById(R.id.tv_delete);

        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }
    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        mDeleteClickListener = listener;
    }
    /**
     * item点击监听器
     */
    public interface OnItemClickListener {
        /**
         * item点击回调
         *
         * @param adapter  The Adapter where the click happened.
         * @param v        The view that was clicked.
         * @param position The position of the view in the adapter.
         */
        void onItemClick(RecyclerView.Adapter adapter, View v, int position);

    }
}
