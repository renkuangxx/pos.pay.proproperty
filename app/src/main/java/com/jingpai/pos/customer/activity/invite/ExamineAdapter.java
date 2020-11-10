package com.jingpai.pos.customer.activity.invite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.ContractFilesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86173
 */
public class ExamineAdapter extends RecyclerView.Adapter<ExamineAdapter.ExamineViewHolder> {
    private Context context;
    private List<ContractFilesBean> contractFilesBeans;
    public ExamineAdapter(Context context) {
        this.context = context;
    }
    public ExamineAdapter(Context context,List<ContractFilesBean> list) {
        this.context = context;
        this.contractFilesBeans = list;
        contractFilesBeans = new ArrayList<>();
    }

    public void setData(List<ContractFilesBean> list){
        contractFilesBeans.clear();
        contractFilesBeans.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExamineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_examine, parent, false);
        ExamineViewHolder holder = new ExamineViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamineViewHolder holder, int position) {
        if (contractFilesBeans.size() <= 0 || position > contractFilesBeans.size() || contractFilesBeans.get(position) ==null) return;
        String url = contractFilesBeans.get(position).getFileUrl()==null?"":contractFilesBeans.get(position).getFileUrl();
        Glide.with(context)
                .load(url)
                .into(holder.ivHetong);
    }

    @Override
    public int getItemCount() { //todo
        return contractFilesBeans == null?0:contractFilesBeans.size();
    }

    public class ExamineViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHetong;
        public ExamineViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHetong = itemView.findViewById(R.id.iv_hetong);
        }
    }
}
