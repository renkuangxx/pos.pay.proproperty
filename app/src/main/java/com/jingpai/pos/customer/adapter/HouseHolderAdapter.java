package com.jingpai.pos.customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.authentication.activity.YezhuDetailActivity;
import com.jingpai.pos.customer.activity.housemember.HouseHolderActivity;
import com.jingpai.pos.customer.bean.HouseHolderDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86173
 */
public class HouseHolderAdapter extends RecyclerView.Adapter<HouseHolderAdapter.HouseHolderViewHolder> {
    private Context mContext;
    private List<HouseHolderDataBean> list;

    public HouseHolderAdapter(Context mContext, List<HouseHolderDataBean> list) {
        this.mContext = mContext;
        list = new ArrayList<>();
        this.list = list;
    }

    public void setData(List<HouseHolderDataBean> list1){
        list.clear();
        list.addAll(list1);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HouseHolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_house_holder, parent, false);
        HouseHolderViewHolder holder = new HouseHolderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HouseHolderViewHolder holder, int position) {
        if (list.size() <= 0 || position > list.size() || list.get(position) ==null) return;
        String name = TextUtils.isEmpty(list.get(position).getMemberName())?"":list.get(position).getMemberName();
        String phone = TextUtils.isEmpty(list.get(position).getMemberPhone())?"":list.get(position).getMemberPhone();
        String time = TextUtils.isEmpty(list.get(position).getApplyTime())?"":list.get(position).getApplyTime();
        String roomNo = TextUtils.isEmpty(list.get(position).getRoomNo())?"":list.get(position).getRoomNo();
        String auditId = TextUtils.isEmpty(list.get(position).getAuditId())?"":list.get(position).getAuditId();
        String state = TextUtils.isEmpty(list.get(position).getAuditStateName())?"":list.get(position).getAuditStateName();
        String auditType = TextUtils.isEmpty(list.get(position).getCertificateType())?"":list.get(position).getCertificateType();
        String idCard = TextUtils.isEmpty(list.get(position).getIdCard())?"":list.get(position).getIdCard();
        String addType = TextUtils.isEmpty(list.get(position).getAddType())?"":list.get(position).getAddType();

        switch (list.get(position).getAuditState()) {
            case 0://"审核中
                holder.tvState.setTextColor(mContext.getResources().getColor(R.color.main));
                break;
            case 1://审核通过
                holder.tvState.setTextColor(mContext.getResources().getColor(R.color.bg_front_default));
                break;
            case 2://已拒绝
                holder.tvState.setTextColor(mContext.getResources().getColor(R.color.text_97));
                break;
        }
        holder.tvName.setText(name);
        holder.tvState.setText(state);
        holder.tvTime.setText(time);
        holder.tvRoom.setText(roomNo);

        if (TextUtils.equals(addType,"MEMBER_ADD")) {
            holder.tvName1.setText("成员姓名");
            holder.rl_jump.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, HouseHolderActivity.class);
                    intent.putExtra("auditId", auditId);
                    mContext.startActivity(intent);
                }
            });
        }else {
            holder.tvName1.setText("申请人");
//        holder.tvTel.setText(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));

//        if (TextUtils.isEmpty(phone) ){
//            if ("OTHER".equals(auditType)) {
//                holder.tv_tel1.setText("其他类型");
//                holder.tvTel.setText(idCard.replaceAll(idCard.substring(1,idCard.length()-1),"****"));
//            } else if ("PASSPORT".equals(auditType)) {
//                holder.tv_tel1.setText("护照");
//                holder.tvTel.setText(idCard.replaceAll(idCard.substring(1,idCard.length()-1),"****"));
//            } else if ("ID_CARD".equals(auditType)) {
//                holder.tv_tel1.setText("身份证");
//                holder.tvTel.setText(idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})","$1****$2"));
//            }
////                holder.tv_tel1.setText("证件号");
//        }
            holder.rl_jump.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, YezhuDetailActivity.class);
                    intent.putExtra("auditId", auditId);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HouseHolderViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvName1;
        private TextView tvState;
        private TextView tvRoom,tvRoom1;
        private TextView tvTime;
        private RelativeLayout rl_jump;

        public HouseHolderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvName1 = itemView.findViewById(R.id.tv_name1);
            tvState = itemView.findViewById(R.id.tv_state);
            tvRoom = itemView.findViewById(R.id.tv_room);
            tvRoom1 = itemView.findViewById(R.id.tv_room1);
            tvTime = itemView.findViewById(R.id.tv_time);
            rl_jump = itemView.findViewById(R.id.rl_jump);
        }
    }
}
