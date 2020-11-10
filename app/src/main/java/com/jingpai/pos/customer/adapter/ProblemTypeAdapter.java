package com.jingpai.pos.customer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间: 2020/2/19
 * 功能:
 */
public class ProblemTypeAdapter extends RecyclerView.Adapter<ProblemTypeAdapter.ProblemTypeHolder> {

    private Context context;
    private List<String>  mDatas;
    private List<Boolean> isClicks;

    public ProblemTypeAdapter(Context context, List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        isClicks = new ArrayList<>();
        for(int i = 0;i<mDatas.size();i++){
            isClicks.add(false);
        }
    }

    @NonNull
    @Override
    public ProblemTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.problem_type_item, parent, false);
        ProblemTypeHolder holder = new ProblemTypeHolder(itemView);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ProblemTypeHolder holder, int position) {
        String s = mDatas.get(position);
        holder.text.setText(s);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                一定要刷新适配器 当条目发生改变这是必须的
                getListener.onClick(position);
                notifyDataSetChanged();

            }
        });
//        如果下标和传回来的下标相等 那么确定是点击的条目 把背景设置一下颜色
        if (position == getmPosition()) {
            holder.text.setBackgroundResource(R.drawable.tv_back);
            holder.text.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
//            否则的话就全白色初始化背景
            holder.text.setBackgroundResource(R.drawable.ic_back_false);
            holder.text.setTextColor(Color.parseColor("#979797"));
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ProblemTypeHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ProblemTypeHolder(@NonNull View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public interface GetListener {

        void onClick(int position);
    }

    private GetListener getListener;

    public void setGetListener(GetListener getListener) {
        this.getListener = getListener;
    }
    private  int mPosition;

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

}
