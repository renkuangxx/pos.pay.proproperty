package com.jingpai.pos.customer.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.ButtonListBean;
import com.jingpai.pos.customer.utils.GlideUtils;
import com.jingpai.pos.customer.utils.JumpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86173
 * jie ou
 */
public class ButtonListAdapter extends RecyclerView.Adapter<ButtonListAdapter.ButtonListViewHolder> {
    private List<ButtonListBean> listBeanList = new ArrayList<>();
    private Context mContext;
    private String linkUrl;
    private String title;
    private String icon;
    private Dialog dialog;
    OnClick myOnClick;
    //接口回调
    public interface OnClick{
        public void onMoreFunclick();
        public void onItemclick(String finalLinkUrl);
    }
    public ButtonListAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setClickListener(OnClick okListener) {
        this.myOnClick = okListener;
    }
    public ButtonListAdapter(List<ButtonListBean> listBeanList, Context mContext) {
        this.listBeanList = listBeanList;
        this.mContext = mContext;
    }

    public void setDataChange(List<ButtonListBean> buttonListBeans) {
        listBeanList.clear();
        listBeanList.addAll(buttonListBeans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ButtonListAdapter.ButtonListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button_list, parent, false);
        ButtonListViewHolder holder = new ButtonListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonListAdapter.ButtonListViewHolder holder, int position) {
        if (listBeanList.size() <= 0 || position > listBeanList.size() || listBeanList.get(position) ==null) return;
        title = listBeanList.get(position).getTitle()==null?"":listBeanList.get(position).getTitle();

        linkUrl = listBeanList.get(position).getUrl()==null?"":listBeanList.get(position).getUrl();
        String finalLinkUrl = JumpUtil.containString(linkUrl);
        holder.tv_title_icon.setText(title);
        if ("全部".equals(title)){
            int localicon = listBeanList.get(position).getLocalIcon();
            Glide.with(mContext).load(localicon).into(holder.iv_icon);
            holder.ll_father.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myOnClick != null) {
                        myOnClick.onMoreFunclick();
                    }
                }
            });
        }else{
            icon = listBeanList.get(position).getIcon()==null?"":listBeanList.get(position).getIcon();
            GlideUtils.LoadingImg(mContext,icon,holder.iv_icon);
            holder.ll_father.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myOnClick != null) {
                        if (listBeanList.get(position).isUsable()){
                            myOnClick.onItemclick(finalLinkUrl);
                        }else{
                            myOnClick.onItemclick("");
                        }

                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listBeanList == null ? 0 : listBeanList.size();
    }

    public class ButtonListViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title_icon;
        private LinearLayout ll_father;
        private ImageView iv_icon;
        public ButtonListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title_icon = itemView.findViewById(R.id.tv_title_icon);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            ll_father = itemView.findViewById(R.id.ll_father);
        }
    }
}
